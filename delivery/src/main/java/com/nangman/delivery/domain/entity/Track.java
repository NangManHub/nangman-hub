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

@Entity
@Table(name = "p_track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "delivery_id", updatable = false, nullable = false)
    private Delivery delivery;

    @Column(name="sequence", nullable = false)
    private int sequence;

    @Column(name="shipper_id", nullable = false)
    private UUID shipperId;

    @Column(name="from_hub_id", nullable = false)
    private UUID fromHubId;

    @Column(name="to_hub_id")
    private UUID toHubId;

    @Column(name="address", nullable = false, length = 50)
    private String address;

    @Column(name="expectDistance", updatable = false, nullable = false)
    private int expectDistance;

    @Column(name="expectTime", updatable = false, nullable = false)
    private int expectTime;

    @Column(name="actualDistance")
    private int actualDistance;

    @Column(name="actualTime")
    private int actualTime;

    @Column(name="status", nullable = false)
    private TrackStatus status;

    @Column(name="departure_time")
    private Date departureTime;
}
