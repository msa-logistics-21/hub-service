package com.sparta.hubservice.repository;

import com.sparta.hubservice.domain.HubPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomHubPathRepository {
    Page<HubPath> searchHubPaths(String searchParam, Pageable pageable);
}
