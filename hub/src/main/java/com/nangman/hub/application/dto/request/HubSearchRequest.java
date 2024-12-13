package com.nangman.hub.application.dto.request;

import java.util.UUID;

public record HubSearchRequest(
        String name,
        String address,
        Double latitude,
        Double longitude,
        UUID managerId
) {
}
