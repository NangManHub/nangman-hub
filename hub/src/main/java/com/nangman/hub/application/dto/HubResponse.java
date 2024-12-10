package com.nangman.hub.application.dto;

import com.nangman.hub.domain.entity.Hub;
import lombok.Builder;

import java.util.UUID;

@Builder
public record HubResponse(
        UUID hubId,
        String name,
        String address,
        Double latitude,
        Double longitude,
        UUID managerId,
        BaseDto baseDto
) {
    public static HubResponse from(Hub hub) {
        return HubResponse.builder()
                .hubId(hub.getId())
                .name(hub.getName())
                .address(hub.getAddress())
                .latitude(hub.getLatitude())
                .longitude(hub.getLongitude())
                .managerId(hub.getManagerId())
                .baseDto(BaseDto.from(hub))
                .build();
    }
}
