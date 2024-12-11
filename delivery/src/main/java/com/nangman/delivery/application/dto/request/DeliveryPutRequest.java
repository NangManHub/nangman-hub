package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.DeliveryStatus;
import java.util.UUID;
import lombok.NonNull;

public record DeliveryPutRequest(
        @NonNull DeliveryStatus status,
        @NonNull UUID fromHubId,
        UUID toHubId,
        String address,
        @NonNull UUID recipient,
        @NonNull UUID orderId
) {}
