package com.nangman.order.application.dto;

import java.util.List;
import java.util.UUID;

public record DeliveryDto(
        UUID id,
        UUID fromHubId,
        UUID toHubId,
        String address,
        UUID recipient,
        UUID orderId,
        List<TrackDto> tracks
) {
}
