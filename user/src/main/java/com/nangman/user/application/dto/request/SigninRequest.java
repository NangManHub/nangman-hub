package com.nangman.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SigninRequest(
        @NotBlank
        @Pattern(regexp = "^.{0,10}$", message = "아이디는 최대 10자까지만 가능합니다.")
        String username,
        @NotBlank String password
) {}
