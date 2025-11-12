package com.sparta.hubservice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class DeleteHubPathResDto {
    private List<UUID> hubPathIds;
    private String message;
}
