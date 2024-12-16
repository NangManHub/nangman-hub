package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.Delivery;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
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
