package com.nangman.slack.application.dto.kafka;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record TrackResponse(
        UUID trackId,
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