package com.nangman.delivery.application.dto.response;

import com.nangman.delivery.domain.entity.Track;
import com.nangman.delivery.domain.entity.TrackStatus;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TrackResponse(
        UUID deliveryId,
        int sequence,
        UUID shipperId,
        UUID fromHubId,
        UUID toHubId,
        String address,
        int expectDistance,
        int expectTime,
        int actualDistance,
        int actualTime,
        TrackStatus status,
        Date departureTime
) {
    public static TrackResponse from(Track track) {
        return TrackResponse.builder()
                .deliveryId(track.getDelivery().getId())
                .sequence(track.getSequence())
                .shipperId(track.getShipperId())
                .fromHubId(track.getFromHubId())
                .toHubId(track.getToHubId())
                .address(track.getAddress())
                .expectDistance(track.getExpectDistance())
                .expectTime(track.getExpectTime())
                .actualDistance(track.getActualDistance())
                .actualTime(track.getActualTime())
                .status(track.getStatus())
                .departureTime(track.getDepartureTime())
                .build();
    }
}