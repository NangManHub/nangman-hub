package com.nangman.user.application.dto.response;

import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "유저 정보 수정 응답 DTO")
public record UserPutResponse(
        @Schema(description = "유저 PK")
        UUID id,
        @Schema(description = "로그인 아이디", defaultValue = "user1")
        String username,
        @Schema(description = "이름", defaultValue = "김낭만")
        String name,
        @Schema(description = "권한", defaultValue = "MASTER", allowableValues = {"MASTER", "MANAGER", "SHIPPER", "AGENT"})
        UserRole role,
        @Schema(description = "슬랙아이디", defaultValue = "slack1")
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
