package com.nangman.ai.application.dto.kafka;

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
        String fromHubName,
        UUID toHubId,
        String toHubName,
        String address,
        Integer expectDistance,
        Integer expectTime,
        Integer actualDistance,
        Integer actualTime,
        Date departureTime
) {
}
