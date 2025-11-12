package com.sparta.hubservice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateHubPathReqDto {
    private Integer distance;
    private Integer duration;
    private Long userId;
}
