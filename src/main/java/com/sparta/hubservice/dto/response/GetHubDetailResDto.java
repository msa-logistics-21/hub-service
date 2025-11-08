package com.sparta.hubservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
public class GetHubDetailResDto {
    private UUID hubId;
    private String hubName;
    private Long userId;
    private String hubAddress;
    private Double longitude;
    private Double latitude;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @QueryProjection
    public GetHubDetailResDto(UUID hubId, String hubName, Long userId, String hubAddress, Double longitude, Double latitude, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.hubId = hubId;
        this.hubName = hubName;
        this.userId = userId;
        this.hubAddress = hubAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
