package com.sparta.hubservice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class CreateHubPathReqDto {
    private UUID startHubId;
    private UUID endHubId;
    private Integer duration;
    private Integer distance;
}
