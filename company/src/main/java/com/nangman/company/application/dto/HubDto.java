package com.nangman.company.application.dto;

import java.util.UUID;

public record HubDto(
        UUID hubId,
        String name,
        String address,
        UserDto manager
) {
}
