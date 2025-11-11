package com.sparta.hubservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoGetPathResDto {
    private List<Path> paths;

    @Getter @Setter
    public static class Path {
        private Summary summary;
    }

    @Getter @Setter
    public static class Summary {
        private double distance; // m 단위
        private int duration; // 초 단위
    }
}
