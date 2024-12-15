package com.nangman.user.application.dto.request;

import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "회원가입 요청 DTO")
public record SignupRequest(
        // TODO: 아이디, 비밀번호 정규식 추가
        @Schema(description = "로그인 아이디", defaultValue = "user1")
        @NotBlank
        @Pattern(regexp = "^.{0,10}$", message = "아이디는 최대 10자까지만 가능합니다.")
        String username,

        @Schema(description = "비밀번호", defaultValue = "password123!")
        @NotBlank String password,

        @Schema(description = "이름", defaultValue = "김낭만")
        @NotBlank String name,

        @Schema(description = "권한", defaultValue = "MASTER", allowableValues = {"MASTER", "MANAGER", "SHIPPER", "AGENT"})
        @NotNull UserRole role,

        @Schema(description = "슬랙아이디", defaultValue = "slack1")
        @NotBlank String slackId
) {
        public User toEntity(String encodedPassword){
                return User.builder()
                        .username(username)
                        .password(encodedPassword)
                        .name(name)
                        .role(role)
                        .slackId(slackId)
                        .build();
        }
}
