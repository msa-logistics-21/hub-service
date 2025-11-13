package com.sparta.hubservice.service;

import com.sparta.hubservice.domain.Hub;
import com.sparta.hubservice.domain.HubPath;
import com.sparta.hubservice.dto.request.CreateHubPathReqDto;
import com.sparta.hubservice.dto.request.DeleteHubPathReqDto;
import com.sparta.hubservice.dto.request.UpdateHubPathReqDto;
import com.sparta.hubservice.dto.response.*;
import com.sparta.hubservice.global.exception.ErrorCode;
import com.sparta.hubservice.global.exception.HubException;
import com.sparta.hubservice.repository.HubPathRepository;
import com.sparta.hubservice.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class HubPathService {

    private final HubPathRepository hubPathRepository;
    private final HubRepository hubRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    // 허브 경로 전체 조회 (검색 + 페이징)
    @Transactional(readOnly = true)
    public Page<GetHubPathResDto> getHubPathPage(String searchParam, Pageable pageable) {
        Page<HubPath> hubPaths = hubPathRepository.searchHubPaths(searchParam, pageable);
        return hubPaths.map(GetHubPathResDto::from);
    }

    // 허브 경로 상세 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "hubPathCache", key = "#hubPathId")
    public GetHubPathDetailResDto getHubPathDetail(UUID hubPathId) {
        HubPath hubPath = hubPathRepository.findById(hubPathId)
                .orElseThrow(() -> new HubException(ErrorCode.HUB_PATH_NOT_FOUND));
        return GetHubPathDetailResDto.from(hubPath);
    }

    // 허브 경로 생성
    public CreateHubPathResDto createHubPath(CreateHubPathReqDto request) {
        Hub startHub = hubRepository.findById(request.getStartHubId())
                .orElseThrow(() -> new HubException(ErrorCode.HUB_NOT_FOUND));
        Hub endHub = hubRepository.findById(request.getEndHubId())
                .orElseThrow(() -> new HubException(ErrorCode.HUB_NOT_FOUND));

        if (hubPathRepository.existsByStartHub_HubIdAndEndHub_HubIdAndDeletedAtIsNull(
                startHub.getHubId(), endHub.getHubId())) {
            throw new HubException(ErrorCode.HUB_PATH_DUPLICATE);
        }

        HubPath hubPath = HubPath.ofNewHubPath(startHub, endHub, request.getDuration(), request.getDistance());
        hubPathRepository.save(hubPath);

        // 캐시에 저장
        GetHubPathDetailResDto cacheDto = GetHubPathDetailResDto.from(hubPath);
        redisTemplate.opsForValue().set("hubPathCache::" + hubPath.getHubPathId(), cacheDto);

        return CreateHubPathResDto.from(hubPath);
    }

    // 허브 경로 수정
    public UpdateHubPathResDto updateHubPath(UUID hubPathId, UpdateHubPathReqDto request) {
        HubPath hubPath = hubPathRepository.findById(hubPathId)
                .orElseThrow(() -> new HubException(ErrorCode.HUB_PATH_NOT_FOUND));

        hubPath.update(request);
        hubPathRepository.save(hubPath);

        // 캐시 갱신
        GetHubPathDetailResDto cacheDto = GetHubPathDetailResDto.from(hubPath);
        redisTemplate.opsForValue().set("hubPathCache::" + hubPathId, cacheDto);

        return UpdateHubPathResDto.from(hubPath);
    }

    // 허브 경로 삭제
    public DeleteHubPathResDto deleteHubPaths(DeleteHubPathReqDto request) {
        List<UUID> requestedIds = request.getHubPathIds();
        List<UUID> deletedIds = new ArrayList<>();
        List<UUID> alreadyDeletedIds = new ArrayList<>();

        for (UUID hubPathId : requestedIds) {
            hubPathRepository.findById(hubPathId)
                    .ifPresentOrElse(path -> {
                        if (path.getDeletedAt() == null) {
                            path.delete(1L); // TODO: 실제 userId로 교체
                            hubPathRepository.save(path);
                            deletedIds.add(path.getHubPathId());

                            // 캐시 제거
                            redisTemplate.delete("hubPathCache::" + hubPathId);
                        } else {
                            alreadyDeletedIds.add(path.getHubPathId());
                        }
                    }, () -> alreadyDeletedIds.add(hubPathId));
        }

        String message = deletedIds.isEmpty()
                ? "삭제할 허브 경로를 찾을 수 없습니다."
                : deletedIds.size() + "건의 허브 경로가 삭제되었습니다.";

        return DeleteHubPathResDto.builder()
                .hubPathIds(deletedIds)
                .message(message)
                .build();
    }

    public HubPathApiResDto getHubPathByHubs(UUID startHubId, UUID endHubId) {
        HubPath hubPath = hubPathRepository.findByStartHub_HubIdAndEndHub_HubId(startHubId, endHubId)
                .orElseThrow(() -> new HubException(ErrorCode.HUB_PATH_NOT_FOUND));

        return new HubPathApiResDto(
                hubPath.getDistance(),
                hubPath.getDuration()
        );
    }
}