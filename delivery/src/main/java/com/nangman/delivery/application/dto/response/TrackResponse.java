package com.nangman.delivery.application.dto.response;

import com.nangman.delivery.domain.entity.Track;
import com.nangman.delivery.domain.enums.TrackStatus;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TrackResponse(
        UUID trackId,
        UUID deliveryId,
        Integer sequence,
        UUID shipperId,
        UUID fromHubId,
        UUID toHubId,
        String address,
        Integer expectDistance,
        Integer expectTime,
        Integer actualDistance,
        Integer actualTime,
        TrackStatus status,
        Date departureTime
) {
    public static TrackResponse from(Track track) {
        return TrackResponse.builder()
                .deliveryId(track.getDelivery().getId())
                .trackId(track.getId())
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