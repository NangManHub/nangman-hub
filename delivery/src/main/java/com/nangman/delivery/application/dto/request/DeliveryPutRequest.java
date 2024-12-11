package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.DeliveryStatus;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record DeliveryPutRequest(
        @NotNull DeliveryStatus status,
        @NotNull UUID fromHubId,
        UUID toHubId,
        String address,
        @NotNull UUID recipient,
        @NotNull UUID orderId
) {}
