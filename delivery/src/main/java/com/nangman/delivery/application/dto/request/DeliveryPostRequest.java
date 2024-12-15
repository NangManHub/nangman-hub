package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.Delivery;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record DeliveryPostRequest(
        @NotNull UUID fromHubId,
        @NotNull UUID toHubId,
        @NotNull String address,
        @NotNull UUID recipient,
        @NotNull UUID orderId
) {

    public Delivery toEntity() {
        return Delivery.builder()
                .fromHubId(fromHubId)
                .toHubId(toHubId)
                .address(address)
                .recipient(recipient)
                .orderId(orderId)
                .build();
    }

}
