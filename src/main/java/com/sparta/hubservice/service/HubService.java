package com.sparta.hubservice.service;

import com.sparta.hubservice.domain.Hub;
import com.sparta.hubservice.dto.request.CreateHubReqDto;
import com.sparta.hubservice.dto.request.DeleteHubReqDto;
import com.sparta.hubservice.dto.request.UpdateHubReqDto;
import com.sparta.hubservice.dto.response.*;
import com.sparta.hubservice.global.exception.ErrorCode;
import com.sparta.hubservice.global.exception.HubException;
import com.sparta.hubservice.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HubService {
    private final HubRepository hubRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    // todo : 유저 관련 처리 (마스터 유저만 생성, 수정, 삭제 가능)

    // 허브 전체 조회
    @Transactional(readOnly = true)
    public Page<GetHubPageResDto> getHubPage(String searchParam, Pageable pageable) {
        return hubRepository.findHubPage(searchParam, pageable);
    }

    // 허브 상세 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "hubCache", key = "#hubId")
    public GetHubDetailResDto getHubDetail(UUID hubId) {
        return hubRepository.findHubDetail(hubId)
                .orElseThrow(() -> new HubException(ErrorCode.HUB_NOT_FOUND));
    }

    // 허브 생성
    @Transactional
    public CreateHubResDto createHub(CreateHubReqDto request) {

        if (hubRepository.existsByHubName(request.getHubName())) {
            throw new HubException(ErrorCode.HUB_DUPLICATE_NAME);
        }

        Hub hub = Hub.ofNewHub(request.getHubName(),
                request.getHubAddress(),
                request.getLatitude(),
                request.getLongitude());

        hubRepository.save(hub);

        return new CreateHubResDto(
                hub.getHubId(),
                hub.getHubName(),
                request.getUserId(),
                hub.getHubAddress(),
                hub.getLongitude(),
                hub.getLatitude(),
                hub.getCreatedAt());
    }

    // 허브 수정
    @Transactional
    @CacheEvict(cacheNames = {"hubCache"}, key = "#hubId")
    public UpdateHubResDto updateHub(UUID hubId, UpdateHubReqDto request) {
        Hub hub = hubRepository.findById(hubId)
                .orElseThrow(() -> new HubException(ErrorCode.HUB_NOT_FOUND));

        hub.update(request);
        return new UpdateHubResDto(
                hub.getHubId(),
                hub.getHubName(),
                request.getUserId(),
                hub.getHubAddress(),
                hub.getLongitude(),
                hub.getLatitude(),
                hub.getUpdatedAt());
    }

    // 허브 삭제
    @Transactional
    public DeleteHubResDto deleteHub(DeleteHubReqDto request) {

        List<UUID> requestedIds = request.getHubIds();
        List<UUID> deletedIds = new ArrayList<>();
        List<UUID> alreadyDeletedIds = new ArrayList<>();

        for (UUID hubId : requestedIds) {
            hubRepository.findById(hubId)
                    .ifPresentOrElse(hub -> {
                        if (hub.getDeletedAt() == null) {
                            hub.delete(1L); // 수정 예정
                            hubRepository.save(hub);
                            deletedIds.add(hub.getHubId());

                            String cacheKey = "hubCache::" + hubId;
                            redisTemplate.delete(cacheKey);

                        } else {
                            alreadyDeletedIds.add(hub.getHubId());
                        }
                    }, () -> {
                        alreadyDeletedIds.add(hubId);
                    });
        }

        String message;
        if (deletedIds.isEmpty()) {
            message = "삭제할 허브를 찾을 수 없습니다.";
        } else {
            message = deletedIds.size() + "건의 허브가 삭제되었습니다.";
        }

        return DeleteHubResDto.builder()
                .hubIds(deletedIds)
                .message(message)
                .build();
    }



}
