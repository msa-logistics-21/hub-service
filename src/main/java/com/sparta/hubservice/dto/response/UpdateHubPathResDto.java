package com.sparta.hubservice.dto.response;

import com.sparta.hubservice.domain.HubPath;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UpdateHubPathResDto {
    private UUID hubPathId;
    private int distance;
    private int duration;
    private LocalDateTime updatedAt;

    public static UpdateHubPathResDto from(HubPath path) {
        return UpdateHubPathResDto.builder()
                .hubPathId(path.getHubPathId())
                .distance(path.getDistance())
                .duration(path.getDuration())
                .updatedAt(path.getUpdatedAt())
                .build();
    }
}