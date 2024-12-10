package com.nangman.user.application.dto.response;

import com.nangman.user.domain.entity.User;

import java.util.UUID;

public record SignupResponse(
        UUID id
) {
    public static SignupResponse from(User user){
        return new SignupResponse(user.getId());
    }
}
