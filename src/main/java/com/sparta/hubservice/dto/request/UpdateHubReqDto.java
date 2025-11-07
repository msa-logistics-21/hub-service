package com.sparta.hubservice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateHubReqDto {
    private Long userId;
    private String hubAddress;
    private Double longitude;
    private Double latitude;
}
