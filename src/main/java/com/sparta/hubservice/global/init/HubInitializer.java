package com.sparta.hubservice.global.init;

import com.sparta.hubservice.domain.Hub;
import com.sparta.hubservice.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubInitializer implements ApplicationRunner {

    private final HubRepository hubRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (hubRepository.count() > 0) {
            log.info("허브 데이터가 이미 초기화되었니다.");
            return;
        }

        log.info("허브 데이터 초기화 시작");

        List<Hub> hubs = List.of(
                Hub.ofNewHub("서울특별시 센터", "서울특별시 송파구 송파대로 55", 37.4969143, 127.1121343),
                Hub.ofNewHub("경기 북부 센터", "경기도 고양시 덕양구 권율대로 570", 37.6404095, 126.8737802),
                Hub.ofNewHub("경기 남부 센터", "경기도 이천시 덕평로 257-21", 37.1903115, 127.3760896),
                Hub.ofNewHub("부산광역시 센터", "부산 동구 중앙대로 206", 35.11571, 129.0428216),
                Hub.ofNewHub("대구광역시 센터", "대구 북구 태평로 161", 35.8758026, 128.5959092),
                Hub.ofNewHub("인천광역시 센터", "인천 남동구 정각로 29", 37.4558148, 126.7062842),
                Hub.ofNewHub("광주광역시 센터", "광주 서구 내방로 111", 35.1597705, 126.8515218),
                Hub.ofNewHub("대전광역시 센터", "대전 서구 둔산로 100", 36.3501707, 127.3848389),
                Hub.ofNewHub("울산광역시 센터", "울산 남구 중앙로 201", 35.5388808, 129.3115449),
                Hub.ofNewHub("세종특별자치시 센터", "세종특별자치시 한누리대로 2130", 36.4799427, 127.289059),
                Hub.ofNewHub("강원특별자치도 센터", "강원특별자치도 춘천시 중앙로 1", 37.882531, 127.7291452),
                Hub.ofNewHub("충청북도 센터", "충북 청주시 상당구 상당로 82", 36.6353828, 127.4914807),
                Hub.ofNewHub("충청남도 센터", "충남 홍성군 홍북읍 충남대로 21", 36.6588409, 126.6736967),
                Hub.ofNewHub("전북특별자치도 센터", "전북특별자치도 전주시 완산구 효자로 225", 35.8200701, 127.1091229),
                Hub.ofNewHub("전라남도 센터", "전남 무안군 삼향읍 오룡길 1", 34.8152658, 126.4573699),
                Hub.ofNewHub("경상북도 센터", "경북 안동시 풍천면 도청대로 455", 36.5753879, 128.5054506),
                Hub.ofNewHub("경상남도 센터", "경남 창원시 의창구 중앙대로 300", 35.237101, 128.6912625)
        );

        hubRepository.saveAll(hubs);
        log.info("허브 데이터 초기화 완료 (총 {}개 허브)", hubs.size());
    }
}

