package com.nangman.hub.application.dto.response;

import com.nangman.hub.application.dto.BaseDto;
import com.nangman.hub.domain.entity.Route;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RouteResponse(
        UUID routeId,
        UUID fromHubId,
        UUID toHubId,
        Integer duration,
        Integer distance,
        BaseDto baseDto
) {
    public static RouteResponse from(Route route) {
        return RouteResponse.builder()
                .routeId(route.getId())
                .fromHubId(route.getFromHub().getId())
                .toHubId(route.getToHub().getId())
                .duration(route.getDuration())
                .distance(route.getDistance())
                .baseDto(BaseDto.from(route))
                .build();
    }
}
