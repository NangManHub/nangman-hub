package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.Delivery;
import com.nangman.delivery.domain.entity.Track;
import java.util.UUID;
import lombok.NonNull;

public record TrackPostRequest(
        int sequence,
        @NonNull
        UUID shipperId,
        @NonNull
        UUID fromHubId,
        UUID toHubId,
        String address,
        int expectDistance,
        int expectTime
) {
    public Track toEntity(Delivery delivery) {
        return Track.builder()
                .delivery(delivery)
                .sequence(sequence)
                .shipperId(shipperId)
                .fromHubId(fromHubId)
                .toHubId(toHubId)
                .address(address)
                .expectDistance(expectDistance)
                .expectTime(expectTime)
                .build();
    }
}