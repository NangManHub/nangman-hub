package com.nangman.user.application.dto.request;

import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignupRequest(
        // TODO: 아이디, 비밀번호 정규식 추가
        @NotBlank
        @Pattern(regexp = "^.{0,10}$", message = "아이디는 최대 10자까지만 가능합니다.")
        String username,

        @NotBlank String password,

        @NotBlank String name,

        @NotNull UserRole role,

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
