package com.sparta.hubservice.domain;

import com.sparta.hubservice.dto.request.UpdateHubReqDto;
import com.sparta.hubservice.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "p_hubs")
public class Hub extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID hubId;

    @Column(nullable = false, length = 100, unique = true)
    private String hubName;

    @Column(nullable = false)
    private String hubAddress;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private Hub(String hubName, String hubAddress, Double latitude, Double longitude) {
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Hub ofNewHub(String hubName, String hubAddress, Double latitude, Double longitude) {
        return new Hub(hubName, hubAddress, latitude, longitude);
    }

    public void update(UpdateHubReqDto request) {
        if (request.getHubAddress() != null) this.hubAddress = request.getHubAddress();
        if (request.getLatitude() != null) this.latitude = request.getLatitude();
        if (request.getLongitude() != null) this.longitude = request.getLongitude();
    }

}
