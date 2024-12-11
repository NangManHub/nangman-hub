package com.nangman.delivery.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_track")
@NoArgsConstructor
public class Track extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", updatable = false, nullable = false)
    private Delivery delivery;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "shipper_id", nullable = false)
    private UUID shipperId;

    @Column(name = "from_hub_id", nullable = false)
    private UUID fromHubId;

    @Column(name = "to_hub_id")
    private UUID toHubId;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "expectDistance", updatable = false, nullable = false)
    private Integer expectDistance;

    @Column(name = "expectTime", updatable = false, nullable = false)
    private Integer expectTime;

    @Column(name = "actualDistance")
    private Integer actualDistance;

    @Column(name = "actualTime")
    private Integer actualTime;

    @Column(name = "status", nullable = false)
    private TrackStatus status;

    @Column(name = "departure_time")
    private Date departureTime;

    @Builder
    public Track(Delivery delivery, Integer sequence, UUID shipperId, UUID fromHubId, UUID toHubId, String address,
                 Integer expectDistance, Integer expectTime) {
        this.delivery = delivery;
        this.sequence = sequence;
        this.shipperId = shipperId;
        this.fromHubId = fromHubId;
        this.toHubId = toHubId;
        this.address = address;
        this.expectDistance = expectDistance;
        this.expectTime = expectTime;
        this.status = TrackStatus.WAITING;
    }
}
