package com.sparta.hubservice.repository;

import com.sparta.hubservice.dto.response.GetHubDetailResDto;
import com.sparta.hubservice.dto.response.GetHubPageResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CustomHubRepository {
    // 검색 조건, 페이지 정보 기반 허브 목록 동적 조회
    Page<GetHubPageResDto> findHubPage(String searchParam, Pageable pageable);

    // 상세 정보 조회
    Optional<GetHubDetailResDto> findHubDetail(UUID hubId);

    // 허브 이름 중복 체크
    boolean existsByHubName(String hubName);

}
