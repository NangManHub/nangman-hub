package com.nangman.user.application.dto.request;

import com.nangman.user.domain.entity.ShipperType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "배송담당자 수정 요청 DTO")
public record ShipperPutRequest(
        @Schema(description = "허브 PK")
        @NotNull UUID hubId,
        @Schema(description = "배송담당자 타입", defaultValue = "HUB", allowableValues = {"HUB", "COMPANY"})
        @NotNull ShipperType type
) {}
