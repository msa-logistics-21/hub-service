package com.sparta.hubservice.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
public class GetHubResDto {
    private UUID hubId;
    private String hubName;
    private String hubAddress;
    private Double longitude;
    private Double latitude;

    @QueryProjection
    public GetHubResDto(UUID hubId, String  hubName, String hubAddress, Double longitude, Double latitude) {
        this.hubId = hubId;
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}