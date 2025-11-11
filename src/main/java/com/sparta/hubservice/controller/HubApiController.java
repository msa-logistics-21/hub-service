package com.sparta.hubservice.controller;

import com.sparta.hubservice.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hubs/api")
public class HubApiController {

    private final HubService hubService;

    @GetMapping("/{hubId}")
    public boolean existsHub(@PathVariable UUID hubId) {
        return hubService.existsHub(hubId);
    }

    @GetMapping("/{hubId}/name")
    public String getHubName(@PathVariable UUID hubId) {
        return hubService.getHubNameById(hubId);
    }
}