package com.nangman.hub.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_routes")
public class Route extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_hub_id", nullable = false)
    private Hub fromHub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_hub_id", nullable = false)
    private Hub toHub;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer distance;

    @Builder
    public Route(Hub fromHub, Hub toHub, Integer duration, Integer distance) {
        this.fromHub = fromHub;
        this.toHub = toHub;
        this.duration = duration;
        this.distance = distance;
    }

    public void update(Hub fromHub, Hub toHub, Integer duration, Integer distance) {
        this.fromHub = fromHub;
        this.toHub = toHub;
        this.duration = duration;
        this.distance = distance;
    }
}
