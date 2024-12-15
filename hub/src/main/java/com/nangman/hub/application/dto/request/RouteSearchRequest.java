package com.nangman.hub.application.dto.request;

import java.util.UUID;

public record RouteSearchRequest(
        UUID fromHubId,
        UUID toHubId,
        Integer duration,
        Integer distance
) {
}
