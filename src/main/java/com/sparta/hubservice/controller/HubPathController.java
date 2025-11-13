package com.sparta.hubservice.controller;

import com.sparta.hubservice.dto.request.*;
import com.sparta.hubservice.dto.response.*;
import com.sparta.hubservice.global.response.ApiResponse;
import com.sparta.hubservice.service.HubPathService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


import java.util.UUID;

@RestController
@RequestMapping("/v1/hub-paths")
@RequiredArgsConstructor
public class HubPathController {

    private final HubPathService hubPathService;

    // 허브 경로 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<GetHubPathResDto>>> getHubPathPage(
            @RequestParam(required = false) String searchParam,
            @PageableDefault(size = 10, sort = { "createdAt", "updatedAt" }, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<GetHubPathResDto> response = hubPathService.getHubPathPage(searchParam, pageable);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    // 허브 경로 상세 조회 (캐시 적용)
    @GetMapping("/{hubPathId}")
    public ResponseEntity<ApiResponse<GetHubPathDetailResDto>> getHubPathDetail(
            @PathVariable UUID hubPathId
    ) {
        GetHubPathDetailResDto response = hubPathService.getHubPathDetail(hubPathId);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    // 허브 경로 생성
    @PostMapping
    public ResponseEntity<ApiResponse<CreateHubPathResDto>> createHubPath(
            @RequestBody CreateHubPathReqDto request
    ) {
        CreateHubPathResDto response = hubPathService.createHubPath(request);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    // 허브 경로 수정
    @PutMapping("/{hubPathId}")
    public ResponseEntity<ApiResponse<UpdateHubPathResDto>> updateHubPath(
            @PathVariable UUID hubPathId,
            @RequestBody UpdateHubPathReqDto request
    ) {
        UpdateHubPathResDto response = hubPathService.updateHubPath(hubPathId, request);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    // 허브 경로 삭제
    @DeleteMapping
    public ResponseEntity<ApiResponse<DeleteHubPathResDto>> deleteHubPaths(
            @RequestBody DeleteHubPathReqDto request
    ) {
        DeleteHubPathResDto response = hubPathService.deleteHubPaths(request);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }
}
