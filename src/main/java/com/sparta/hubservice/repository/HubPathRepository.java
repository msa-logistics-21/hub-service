package com.sparta.hubservice.repository;

import com.sparta.hubservice.domain.Hub;
import com.sparta.hubservice.domain.HubPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HubPathRepository extends JpaRepository<HubPath, UUID> {
    boolean existsByStartHubAndEndHub(Hub startHub, Hub endHub);
}
