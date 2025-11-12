package com.sparta.hubservice.dto.response;

import com.sparta.hubservice.domain.HubPath;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class GetHubPathResDto {
    private UUID hubPathId;
    private String startHubName;
    private String endHubName;
    private int distance;
    private int duration;

    public static GetHubPathResDto from(HubPath path) {
        return GetHubPathResDto.builder()
                .hubPathId(path.getHubPathId())
                .startHubName(path.getStartHub().getHubName())
                .endHubName(path.getEndHub().getHubName())
                .distance(path.getDistance())
                .duration(path.getDuration())
                .build();
    }
}