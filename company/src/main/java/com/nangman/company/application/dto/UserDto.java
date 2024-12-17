package com.nangman.company.application.dto;

import com.nangman.company.domain.enums.UserRole;
import java.util.UUID;

public record UserDto(
        UUID userId,
        String username,
        String name,
        UserRole role,
        String slackId
) {
    public static UserDto createTmpUser() {
        return new UserDto(
                UUID.randomUUID(),
                "username",
                "name",
                UserRole.AGENT,
                "slackId"
        );
    }
}
