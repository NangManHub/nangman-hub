package com.nangman.user.application.dto.response;

import com.nangman.user.domain.entity.Shipper;

import java.util.UUID;

public record ShipperPostResponse(
        UUID id
) {
    public static ShipperPostResponse from(Shipper shipper){
        return new ShipperPostResponse(shipper.getId());
    }
}
