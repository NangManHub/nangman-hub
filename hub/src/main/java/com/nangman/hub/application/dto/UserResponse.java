package com.nangman.hub.application.dto;

import java.util.UUID;

public record UserResponse(
        UUID userId,
        String username,
        String name,
        UserRole role,
        String slackId
) {
}
