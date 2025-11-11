package com.sparta.hubservice.repository;

import com.sparta.hubservice.domain.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HubRepository extends JpaRepository<Hub, UUID>, CustomHubRepository {
    boolean existsByHubIdAndDeletedAtIsNull(UUID hubId);
}
