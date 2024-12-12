package com.nangman.user.application.dto.request;

import com.nangman.user.domain.entity.Shipper;
import com.nangman.user.domain.entity.ShipperType;
import com.nangman.user.domain.entity.User;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ShipperPostRequest(
        @NotNull UUID userId,
        @NotNull UUID hubId,
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
