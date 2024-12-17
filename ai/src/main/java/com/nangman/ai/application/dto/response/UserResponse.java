package com.nangman.ai.application.dto.response;

import java.util.UUID;

public record UserResponse(
        UUID userId,
        String name,
        String slackId
) {
}
