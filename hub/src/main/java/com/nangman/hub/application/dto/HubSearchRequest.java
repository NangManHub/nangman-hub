package com.nangman.hub.application.dto;

import java.util.UUID;

public record HubSearchRequest(
        String name,
        String address,
        Double latitude,
        Double longitude,
        UUID managerId
) {
}
