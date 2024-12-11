package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.DeliveryStatus;
import java.util.UUID;

public record DeliverySearchRequest (
    DeliveryStatus status,
    UUID fromHubId,
    UUID toHubId,
    String address,
    UUID recipient,
    UUID orderId
) {}
