package com.nangman.hub.application.dto.request;

import com.nangman.hub.domain.entity.Hub;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record HubPostRequest(
        @NotNull
        String name,
        @NotNull
        String address,
        @NotNull
        Double latitude,
        @NotNull
        Double longitude,
        @NotNull
        UUID managerId,
        UUID parentHubId
) {
    public Hub toEntity(Hub parentHub) {
        return Hub.builder()
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .managerId(managerId)
                .parentHub(parentHub)
                .build();
    }
}
