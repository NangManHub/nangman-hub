package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.Delivery;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record DeliveryPostRequest(
        @NotNull
        UUID fromHubId,
        UUID toHubId,
        String address,
        @NotNull
        UUID recipient,
        @NotNull
        UUID orderId,
        List<TrackPostRequest> tracks) {

    public Delivery toEntity() {
        Delivery delivery = Delivery.builder()
                .fromHubId(fromHubId)
                .toHubId(toHubId)
                .address(address)
                .recipient(recipient)
                .orderId(orderId)
                .build();

        tracks.forEach(track -> delivery.updateTrack(track.toEntity(delivery)));

        return delivery;
    }
}
