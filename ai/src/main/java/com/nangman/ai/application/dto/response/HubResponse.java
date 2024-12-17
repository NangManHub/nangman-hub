package com.nangman.ai.application.dto.response;

import java.util.UUID;

public record HubResponse(
        UUID hubId,
        String name,
        UUID managerId
) {
}
