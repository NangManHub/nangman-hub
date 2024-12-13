package com.nangman.user.application.dto.request;

import com.nangman.user.domain.entity.ShipperType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ShipperPutRequest(
        @NotNull UUID hubId,
        @NotNull ShipperType type
) {}
