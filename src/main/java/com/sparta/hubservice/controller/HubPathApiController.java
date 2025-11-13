package com.sparta.hubservice.controller;

import com.sparta.hubservice.dto.response.HubPathApiResDto;
import com.sparta.hubservice.global.response.ApiResponse;
import com.sparta.hubservice.service.HubPathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hub-paths/api")
public class HubPathApiController {

    private final HubPathService hubPathService;

    @GetMapping
    public ResponseEntity<ApiResponse<HubPathApiResDto>> getHubPathByHubs(
            @RequestParam UUID startHubId,
            @RequestParam UUID endHubId
    ) {
        HubPathApiResDto response = hubPathService.getHubPathByHubs(startHubId, endHubId);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }
}
