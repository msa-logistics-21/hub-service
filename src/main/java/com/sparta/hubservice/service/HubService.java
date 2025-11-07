package com.sparta.hubservice.service;

import com.sparta.hubservice.domain.Hub;
import com.sparta.hubservice.dto.request.CreateHubReqDto;
import com.sparta.hubservice.dto.request.DeleteHubReqDto;
import com.sparta.hubservice.dto.request.UpdateHubReqDto;
import com.sparta.hubservice.dto.response.CreateHubResDto;
import com.sparta.hubservice.dto.response.DeleteHubResDto;
import com.sparta.hubservice.dto.response.GetHubResDto;
import com.sparta.hubservice.dto.response.UpdateHubResDto;
import com.sparta.hubservice.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HubService {
    private final HubRepository hubRepository;

    // todo : 권한 처리 (마스터 유저만 생성, 수정, 삭제 가능)
    // todo : 예외 처리

    // 허브 조회
    @Transactional(readOnly = true)
    public Page<GetHubResDto> getHubPage(String searchParam, Pageable pageable) {
        return hubRepository.findHubPage(searchParam, pageable);
    }

    // 허브 생성
    public CreateHubResDto createHub(CreateHubReqDto request) {

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
                hub.getCreatedAt(),
                hub.getUpdatedAt());
    }

    // 허브 수정
    public UpdateHubResDto updateHub(UUID hubId, UpdateHubReqDto request) {
        Hub hub = hubRepository.findById(hubId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 허브입니다."));

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

        for (UUID hubId : requestedIds) {
            hubRepository.findById(hubId)
                    .filter(hub -> hub.getDeletedAt() == null) // 이미 삭제된 건 제외
                    .ifPresent(hub -> {
                        hub.delete(1L);
                        hubRepository.save(hub);
                        deletedIds.add(hub.getHubId());
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
