package com.sparta.hubservice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteHubPathReqDto {
    private List<UUID> hubPathIds;
}
