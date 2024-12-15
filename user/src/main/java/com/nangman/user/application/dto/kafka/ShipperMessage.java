package com.nangman.user.application.dto.kafka;

import com.nangman.user.domain.entity.Shipper;
import com.nangman.user.domain.entity.ShipperType;
import com.nangman.user.domain.entity.User;

import java.util.UUID;

public record ShipperMessage(
        String action,      // 메세지 타입(create/update/delete)
        UUID userId,        // 유저 PK
        String username,    // 유저 아이디
        String name,        // 유저 이름
        UUID hubId,         // 소속 허브 PK
        ShipperType type    // 담당 분류
) {

    public static ShipperMessage of(String action, Shipper shipper){
        User userInfo = shipper.getUser();
        return new ShipperMessage(action, userInfo.getId(), userInfo.getUsername(), userInfo.getName(), shipper.getHubId(), shipper.getType());
    }
}
