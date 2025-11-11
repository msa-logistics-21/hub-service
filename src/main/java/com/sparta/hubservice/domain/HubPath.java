package com.sparta.hubservice.domain;

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
@Table(name = "p_hub_paths")
public class HubPath extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_path_id")
    private UUID hubPathId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_hub_id", nullable = false)
    private Hub startHub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_hub_id", nullable = false)
    private Hub endHub;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer distance;

    public static HubPath ofNewHubPath(Hub startHub, Hub endHub, int duration, int distance) {
        return HubPath.builder()
                .startHub(startHub)
                .endHub(endHub)
                .duration(duration)
                .distance(distance)
                .build();
    }
}
