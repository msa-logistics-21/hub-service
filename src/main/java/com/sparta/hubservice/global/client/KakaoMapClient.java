package com.sparta.hubservice.global.client;

import com.sparta.hubservice.dto.response.KakaoGetPathResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class KakaoMapClient {

    private final WebClient webClient;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    public KakaoMapClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://apis-navi.kakaomobility.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    public KakaoGetPathResDto getPath(double originLat, double originLon, double destLat, double destLon) {
        String uri = String.format(
                "/v1/directions?origin=%f,%f&destination=%f,%f",
                originLon, originLat, destLon, destLat
        );

        return webClient.mutate()
                .baseUrl("https://apis-navi.kakaomobility.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoApiKey)
                .build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(KakaoGetPathResDto.class)
                .block();
    }
}
