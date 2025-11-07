package com.sparta.hubservice.repository;

import com.sparta.hubservice.dto.response.GetHubResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomHubRepository {
    // 검색 조건, 페이지 정보 기반 허브 목록 동적 조회
    Page<GetHubResDto> findHubPage(String searchParam, Pageable pageable);
}
