package com.nangman.user.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "로그인 요청 DTO")
public record SigninRequest(
        @Schema(description = "로그인 아이디", defaultValue = "user1")
        @NotBlank
        @Pattern(regexp = "^.{0,10}$", message = "아이디는 최대 10자까지만 가능합니다.")
        String username,

        @Schema(description = "비밀번호", defaultValue = "password123!")
        @NotBlank String password
) {}
