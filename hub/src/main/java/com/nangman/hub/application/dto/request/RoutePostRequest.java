package com.nangman.hub.application.dto.request;

import com.nangman.hub.domain.entity.Hub;
import com.nangman.hub.domain.entity.Route;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RoutePostRequest(
        @NotNull
        UUID fromHubId,
        @NotNull
        UUID toHubId,
        @NotNull
        Integer duration,
        @NotNull
        Integer distance
) {
    public Route toEntity(Hub fromHub, Hub toHub) {
        return Route.builder()
                .fromHub(fromHub)
                .toHub(toHub)
                .duration(duration)
                .distance(distance)
                .build();
    }
}
