package com.nangman.delivery.application.dto.feign;

import java.util.UUID;
import lombok.Builder;

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
) {}
