package com.nangman.delivery.domain.entity;

import com.nangman.delivery.common.exception.DomainException;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.domain.enums.TrackStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_tracks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipper_id", nullable = false)
    private Shipper shipper;

    @Column(name = "from_hub_id", nullable = false)
    private UUID fromHubId;

    @Column(name = "to_hub_id")
    private UUID toHubId;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "expect_distance", updatable = false, nullable = false)
    private Integer expectDistance;

    @Column(name = "expect_time", updatable = false, nullable = false)
    private Integer expectTime;

    @Column(name = "actual_distance")
    private Integer actualDistance;

    @Column(name = "actual_time")
    private Integer actualTime;

    @Column(name = "status", nullable = false)
    private TrackStatus status;

    @Column(name = "departure_time")
    private Date departureTime;

    @Column(name = "route_id", nullable = false)
    private UUID routeId;

    @Builder
    public Track(Delivery delivery, Integer sequence, Shipper shipper, UUID fromHubId, UUID toHubId, String address,
                 Integer expectDistance, Integer expectTime, UUID routeId) {
        this.delivery = delivery;
        this.sequence = sequence;
        this.shipper = shipper;
        this.fromHubId = fromHubId;
        this.toHubId = toHubId;
        this.address = address;
        this.expectDistance = expectDistance;
        this.expectTime = expectTime;
        this.status = TrackStatus.WAITING;
        this.routeId = routeId;
    }

    public void update(Integer sequence, Shipper shipper, UUID fromHubId, UUID toHubId, String address, Integer expectDistance,
                       Integer expectTime, Integer actualDistance, Integer actualTime, TrackStatus status, Date departureTime) {
        this.sequence = sequence;
        this.shipper = shipper;
        this.fromHubId = fromHubId;
        this.toHubId = toHubId;
        this.address = address;
        this.expectDistance = expectDistance;
        this.expectTime = expectTime;
        this.actualDistance = actualDistance;
        this.actualTime = actualTime;
        this.status = status;
        this.departureTime = departureTime;
    }

    public void departureTrack() {
        if(this.status != TrackStatus.WAITING) {
            throw new DomainException(ExceptionStatus.TRACK_NOT_WAITING);
        }
        this.status = TrackStatus.MOVING;
        this.departureTime = new Date();
    }

    public void complete(Integer actualDistance) {
        if(this.status != TrackStatus.MOVING) {
            throw new DomainException(ExceptionStatus.TRACK_NOT_MOVING);
        }
        this.actualDistance = actualDistance;
        this.actualTime = (int) Duration.between(departureTime.toInstant(), new Date().toInstant()).toMinutes();
        this.status = TrackStatus.ARRIVE;
    }

    public void updateDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}
