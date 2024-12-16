package com.nangman.order.application.dto.event;

import java.util.UUID;

public record DeliveryEvent(
        UUID id,
        UUID orderId

) {
}
