package com.nangman.slack.application.dto.kafka;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record DeliveryResponse(
        UUID id,
        DeliveryStatus status,
        UUID fromHubId,
        UUID toHubId,
        String address,
        UUID recipient,
        UUID orderId,
        List<TrackResponse> tracks
) {}