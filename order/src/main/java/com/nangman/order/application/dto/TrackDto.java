package com.nangman.order.application.dto;

import java.util.UUID;

public record TrackDto(
        UUID deliveryId,
        Integer sequence,
        UUID shipperId
) {
}
