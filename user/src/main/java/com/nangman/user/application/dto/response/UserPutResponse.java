package com.nangman.user.application.dto.response;

import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.entity.UserRole;

import java.util.UUID;

public record UserPutResponse(
        UUID id,
        String username,
        String name,
        UserRole role,
        String slackId
) {
    public static UserPutResponse from(User user){

        return new UserPutResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getRole(),
                user.getSlackId());
    }
}
