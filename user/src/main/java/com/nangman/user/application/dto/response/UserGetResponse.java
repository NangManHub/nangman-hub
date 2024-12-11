package com.nangman.user.application.dto.response;

import com.nangman.user.domain.entity.UserRole;

import java.util.UUID;

public record UserGetResponse(
        UUID id,
        String username,
        String name,
        UserRole role,
        String slackId,
        Hub hub
) {
    public static UserGetResponse of(UUID id, String username, String name, UserRole role, String slackId, Hub hub) {
        return new UserGetResponse(id, username, name, role, slackId, hub);
    }
}