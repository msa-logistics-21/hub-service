package com.sparta.hubservice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateHubReqDto {
    private String hubName;
    private Long userId;
    private String hubAddress;
    private Double longitude;
    private Double latitude;
}
