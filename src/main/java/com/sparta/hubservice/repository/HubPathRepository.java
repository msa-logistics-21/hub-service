package com.sparta.hubservice.repository;

import com.sparta.hubservice.domain.HubPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HubPathRepository extends JpaRepository<HubPath, UUID>, CustomHubPathRepository{
    boolean existsByStartHub_HubIdAndEndHub_HubIdAndDeletedAtIsNull(UUID startHubId, UUID endHubId);
    Optional<HubPath> findByStartHub_HubIdAndEndHub_HubId(UUID startHubId, UUID endHubId);
}
