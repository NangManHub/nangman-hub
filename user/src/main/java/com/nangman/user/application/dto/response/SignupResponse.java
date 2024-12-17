package com.nangman.user.application.dto.response;

import com.nangman.user.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "회원가입 응답 DTO")
public record SignupResponse(
        @Schema(description = "유저 PK")
        UUID id
) {
    public static SignupResponse from(User user){
        return new SignupResponse(user.getId());
    }
}
