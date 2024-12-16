package com.nangman.delivery.application.dto.feign;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record BaseDto(
        UUID createdBy,
        LocalDateTime createdAt,
        UUID updatedBy,
        LocalDateTime updatedAt,
        UUID deletedBy,
        LocalDateTime deletedAt
) {}