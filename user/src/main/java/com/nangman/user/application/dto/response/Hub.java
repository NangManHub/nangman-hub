package com.nangman.user.application.dto.response;

import java.util.UUID;

public record Hub(
        UUID hubId,
        String name,
        String address,
        Double longitude,
        Double latitude
) {

    public static Hub of(UUID id, String name, String address, Double longitude, Double latitude){
        return new Hub(id, name, address, longitude, latitude);
    }
}
