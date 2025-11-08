package com.sparta.hubservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public CreateHubResDto(UUID hubId, String hubName, Long userId, String hubAddress, Double longitude, Double latitude, LocalDateTime createdAt) {
        this.hubId = hubId;
        this.hubName = hubName;
        this.userId = userId;
        this.hubAddress = hubAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.createdAt = LocalDateTime.now();
    }
}
