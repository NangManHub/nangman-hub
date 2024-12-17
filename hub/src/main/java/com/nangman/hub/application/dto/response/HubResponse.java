package com.nangman.hub.application.dto.response;

import com.nangman.hub.application.dto.BaseDto;
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
        UUID parentHubId,
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
                .parentHubId(hub.getParentHub() != null ? hub.getParentHub().getId() : null)
                .baseDto(BaseDto.from(hub))
                .build();
    }
}
