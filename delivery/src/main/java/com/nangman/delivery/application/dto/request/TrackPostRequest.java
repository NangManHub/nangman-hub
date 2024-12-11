package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.Delivery;
import com.nangman.delivery.domain.entity.Track;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record TrackPostRequest(
        @NotNull Integer sequence,
        @NotNull UUID shipperId,
        @NotNull UUID fromHubId,
        UUID toHubId,
        String address,
        @NotNull Integer expectDistance,
        @NotNull Integer expectTime
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