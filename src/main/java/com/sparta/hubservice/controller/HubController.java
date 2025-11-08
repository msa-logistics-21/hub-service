package com.sparta.hubservice.controller;

import com.sparta.hubservice.global.response.ApiResponse;
import com.sparta.hubservice.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.hubservice.dto.request.*;
import com.sparta.hubservice.dto.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/hubs")
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    // 허브 추가
    @PostMapping
    public ResponseEntity<ApiResponse<CreateHubResDto>> createHub(@RequestBody CreateHubReqDto request) {
        CreateHubResDto response = hubService.createHub(request);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    // 허브 수정
    @PutMapping("/{hubId}")
    public ResponseEntity<ApiResponse<UpdateHubResDto>> updateHub(
            @PathVariable UUID hubId,
            @RequestBody UpdateHubReqDto request
    ) {
        UpdateHubResDto response = hubService.updateHub(hubId, request);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    // 허브 삭제
    @DeleteMapping
    public ResponseEntity<ApiResponse<DeleteHubResDto>> deleteHub(
            @RequestBody DeleteHubReqDto request
    ) {
        DeleteHubResDto response = hubService.deleteHub(request);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    // 허브 리스트 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<GetHubPageResDto>>> getHubPage(
        @RequestParam(required = false) String searchParam,
        @PageableDefault(size = 10, sort = { "createdAt", "updatedAt" }, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<GetHubPageResDto> response = hubService.getHubPage(searchParam, pageable);
        return ResponseEntity.ok(new ApiResponse<>(response));
        }

    // 허브 상세 조회
    @GetMapping("/{hubId}")
    public ResponseEntity<ApiResponse<GetHubDetailResDto>> getHubDetail(@PathVariable UUID hubId) {
        GetHubDetailResDto response = hubService.getHubDetail(hubId);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }
    }



