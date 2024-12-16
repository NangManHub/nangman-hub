package com.nangman.order.application.dto;

import java.util.UUID;

public record HubDto(
        UUID hubId,
        String name,
        String address,
        UUID managerId
) {
}
