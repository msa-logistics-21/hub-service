package com.sparta.hubservice.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
public class GetHubPageResDto {
    private UUID hubId;
    private String hubName;
    private String hubAddress;
    private Double longitude;
    private Double latitude;

    @QueryProjection
    public GetHubPageResDto(UUID hubId, String  hubName, String hubAddress, Double longitude, Double latitude) {
        this.hubId = hubId;
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}