package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.Delivery;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;

public record DeliveryPostRequest(
        @NonNull
        UUID fromHubId,
        UUID toHubId,
        String address,
        @NonNull
        UUID recipient,
        @NonNull
        UUID orderId,
        @NonNull
        List<TrackPostRequest> tracks
) {

    public Delivery toEntity() {
        Delivery delivery = Delivery.builder()
                .fromHubId(fromHubId)
                .toHubId(toHubId)
                .address(address)
                .recipient(recipient)
                .orderId(orderId)
                .build();

        delivery.addTracks(tracks.stream().map(track -> track.toEntity(delivery)).toList());

        return delivery;
    }
}
