package com.sparta.hubservice.global.init;

import com.sparta.hubservice.global.client.KakaoMapClient;
import com.sparta.hubservice.domain.Hub;
import com.sparta.hubservice.domain.HubPath;
import com.sparta.hubservice.dto.response.KakaoGetPathResDto;
import com.sparta.hubservice.repository.HubRepository;
import com.sparta.hubservice.repository.HubPathRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class HubPathInitializer implements ApplicationRunner {

    private final HubRepository hubRepository;
    private final HubPathRepository hubPathRepository;
    private final KakaoMapClient kakaoMapClient;

    @Override
    public void run(ApplicationArguments args) {
        List<Hub> hubs = hubRepository.findAll();

        if (hubs.isEmpty()) {
            log.warn("허브 데이터가 없습니다.");
            return;
        }

        if (hubPathRepository.count() > 0) {
            log.info("허브 경로가 이미 초기화 되었습니다.");
            return;
        }

        log.info("허브 경로 초기화 시작 (총 {}개 허브)", hubs.size());

        AtomicInteger count = new AtomicInteger();

        hubs.parallelStream().forEach(start ->
                hubs.stream()
                        .filter(end -> !start.equals(end))
                        .forEach(end -> {
                            try {
                                KakaoGetPathResDto path = kakaoMapClient.getPath(
                                        start.getLatitude(), start.getLongitude(),
                                        end.getLatitude(), end.getLongitude()
                                );

                                // null 체크
                                if (path == null || path.getRoutes() == null || path.getRoutes().isEmpty()) {
                                    log.warn("경로 없음: {} → {}", start.getHubName(), end.getHubName());
                                    return;
                                }

                                var summary = path.getRoutes().get(0).getSummary();

                                HubPath hubPath = HubPath.ofNewHubPath(
                                        start,
                                        end,
                                        summary.getDuration(),
                                        (int) summary.getDistance()
                                );

                                hubPathRepository.save(hubPath);

                                log.info("{} → {} 거리: {}m, 시간: {}초",
                                        start.getHubName(), end.getHubName(),
                                        summary.getDistance(), summary.getDuration());

                                count.getAndIncrement();

                                Thread.sleep(300);
                            } catch (Exception e) {
                                log.error("경로 계산 실패: {} → {} ({})",
                                        start.getHubName(), end.getHubName(), e.getMessage());
                            }
                        })
        );

        log.info("허브 경로 초기화 완료 (총 {}건)", count.get());
    }
}
