package com.sparta.hubservice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CreateHubResDto {
    private UUID hubId;
    private String hubName;
    private Long userId;
    private String hubAddress;
    private Double longitude;
    private Double latitude;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CreateHubResDto(UUID hubId, String hubName, Long userId, String hubAddress, Double longitude, Double latitude, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.hubId = hubId;
        this.hubName = hubName;
        this.userId = userId;
        this.hubAddress = hubAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.createdAt = getCreatedAt();
        this.updatedAt = getUpdatedAt();
    }
}
