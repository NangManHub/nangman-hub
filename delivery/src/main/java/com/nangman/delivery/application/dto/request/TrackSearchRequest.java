package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.TrackStatus;
import java.util.Date;
import java.util.UUID;

public record TrackSearchRequest (
    UUID deliveryId,
    Integer sequence,
    UUID shipperId,
    UUID fromHubId,
    UUID toHubId,
    String address,
    Integer expectDistance,
    Integer expectTime,
    Integer actualDistance,
    Integer actualTime,
    TrackStatus status,
    Date departureTime
) {}
