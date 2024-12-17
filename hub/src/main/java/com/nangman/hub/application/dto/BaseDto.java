package com.nangman.hub.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nangman.hub.domain.entity.BaseEntity;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record BaseDto(
        UUID createdBy,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime createdAt,
        UUID updatedBy,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime updatedAt,
        UUID deletedBy,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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