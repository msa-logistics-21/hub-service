package com.sparta.hubservice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UpdateHubResDto {
    private UUID hubId;
    private String hubName;
    private Long userId;
    private String hubAddress;
    private Double longitude;
    private Double latitude;
    private LocalDateTime updatedAt;

    public UpdateHubResDto(UUID hubId, String hubName, Long userId, String hubAddress, Double longitude, Double latitude, LocalDateTime updatedAt) {
        this.hubId = hubId;
        this.hubName = hubName;
        this.userId = userId;
        this.hubAddress = hubAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.updatedAt = getUpdatedAt();
    }
}
