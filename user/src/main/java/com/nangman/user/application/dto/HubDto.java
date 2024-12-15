package com.nangman.user.application.dto;

import java.util.UUID;

public record HubDto(
        UUID hubId,
        UUID managerId
) {
}
