package com.nangman.user.application.dto.request;

import com.nangman.user.domain.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "유저 정보 수정 요청 DTO")
public record UserPutRequest(
        @Schema(description = "로그인 아이디", defaultValue = "user1")
        @NotBlank
        @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 4자 이상, 10자 이하의 알파벳 소문자와 숫자로만 구성되어야 합니다.")
        String username,

        @Schema(description = "비밀번호", defaultValue = "Password123!")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,15}$",
                message = "비밀번호는 8자 이상, 15자 이하의 알파벳 대소문자, 숫자, 특수문자를 포함해야 합니다."
        )
        @NotBlank String password,

        @Schema(description = "이름", defaultValue = "김낭만")
        @NotBlank String name,

        @Schema(description = "권한", defaultValue = "MASTER", allowableValues = {"MASTER", "MANAGER", "SHIPPER", "AGENT"})
        @NotNull UserRole role,

        @Schema(description = "슬랙아이디", defaultValue = "slack1")
        @NotBlank String slackId
) {
}
