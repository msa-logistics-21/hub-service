package com.sparta.hubservice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class DeleteHubResDto {
    private List<UUID> hubIds;
    private String message;
}
