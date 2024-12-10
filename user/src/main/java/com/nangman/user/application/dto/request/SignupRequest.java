package com.nangman.user.application.dto.request;

import com.nangman.user.domain.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignupRequest(
        @NotBlank
        @Pattern(regexp = "^.{0,10}$", message = "아이디는 최대 10자까지만 가능합니다.")
        String username,

        @NotBlank String password,

        @NotBlank String name,

        @NotNull UserRole role,

        @NotBlank String slackId
) {}
