package com.nangman.delivery.application.dto.kafka;

import com.nangman.delivery.domain.entity.Shipper;
import com.nangman.delivery.domain.enums.ShipperType;

import java.util.UUID;

public record ShipperMessage(
        String action,      // 메세지 타입(create/update/delete)
        UUID userId,        // 유저 PK
        String username,    // 유저 아이디
        String name,        // 유저 이름
        UUID hubId,         // 소속 허브 PK
        ShipperType type    // 담당 분류
) {
    public Shipper toEntity() {
        return Shipper.builder()
                .id(userId)
                .hubId(hubId)
                .shipperType(type)
                .build();
    }
}