package com.nangman.user.application.dto.response;

import com.nangman.user.domain.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "유저 조회 응답 DTO")
public record UserGetResponse(
        @Schema(description = "유저 PK")
        UUID id,
        @Schema(description = "로그인 아이디", defaultValue = "user1")
        String username,
        @Schema(description = "이름", defaultValue = "김낭만")
        String name,
        @Schema(description = "권한", defaultValue = "MASTER", allowableValues = {"MASTER", "MANAGER", "SHIPPER", "AGENT"})
        UserRole role,
        @Schema(description = "슬랙아이디", defaultValue = "slack1")
        String slackId,
        @Schema(description = "소속 허브")
        Hub hub
) {
    public static UserGetResponse of(UUID id, String username, String name, UserRole role, String slackId, Hub hub) {
        return new UserGetResponse(id, username, name, role, slackId, hub);
    }
}