package com.nangman.delivery.application.dto.feign;

import com.nangman.delivery.domain.entity.Shipper;
import com.nangman.delivery.domain.entity.Track;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RouteDetailResponse(
        UUID routeId,
        HubResponse fromHub,
        HubResponse toHub,
        Integer duration,
        Integer distance,
        BaseDto baseDto
) {
    public Track toTrack(Integer sequence, Shipper shipper) {
        return Track.builder()
                .sequence(sequence)
                .shipper(shipper)
                .routeId(routeId)
                .fromHubId(fromHub.hubId())
                .toHubId(toHub.hubId())
                .expectTime(duration)
                .expectDistance(distance)
                .build();
    }
}
