package com.nangman.hub.application.dto;

import com.nangman.hub.domain.entity.BaseEntity;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record BaseDto(
        UUID createdBy,
        LocalDateTime createdAt,
        UUID updatedBy,
        LocalDateTime updatedAt,
        UUID deletedBy,
        LocalDateTime deletedAt
) {
    public static BaseDto from(BaseEntity baseEntity) {
        return BaseDto.builder()
                .createdBy(baseEntity.getCreatedBy())
                .createdAt(baseEntity.getCreatedAt())
                .updatedBy(baseEntity.getUpdatedBy())
                .updatedAt(baseEntity.getUpdatedAt())
                .deletedBy(baseEntity.getDeletedBy())
                .deletedAt(baseEntity.getDeletedAt())
                .build();
    }
}