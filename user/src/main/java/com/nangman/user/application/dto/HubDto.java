package com.nangman.user.application.dto;

import java.util.UUID;

public record HubDto(
        UUID hubId,
        String name,
        String address,
        Double longitude,
        Double latitude,
        UUID managerId
) {
}
