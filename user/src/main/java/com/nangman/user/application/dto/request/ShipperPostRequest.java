package com.nangman.user.application.dto.request;

import com.nangman.user.domain.entity.Shipper;
import com.nangman.user.domain.entity.ShipperType;
import com.nangman.user.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "배송담당자 생성 요청 DTO")
public record ShipperPostRequest(
        @Schema(description = "유저 PK")
        @NotNull UUID userId,
        @Schema(description = "허브 PK")
        @NotNull UUID hubId,
        @Schema(description = "배송담당자 타입", defaultValue = "HUB", allowableValues = {"HUB", "COMPANY"})
        @NotNull ShipperType type
) {

    public Shipper toEntity(User user) {
        return Shipper.builder()
                .user(user)
                .hubId(hubId)
                .type(type)
                .build();
    }
}
