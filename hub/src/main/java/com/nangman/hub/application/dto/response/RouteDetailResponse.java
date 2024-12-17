package com.nangman.hub.application.dto.response;

import com.nangman.hub.application.dto.BaseDto;
import com.nangman.hub.domain.entity.Route;
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
    public static RouteDetailResponse from(Route route) {
        return RouteDetailResponse.builder()
                .routeId(route.getId())
                .fromHub(HubResponse.from(route.getFromHub()))
                .toHub(HubResponse.from(route.getToHub()))
                .duration(route.getDuration())
                .distance(route.getDistance())
                .baseDto(BaseDto.from(route))
                .build();
    }
}
