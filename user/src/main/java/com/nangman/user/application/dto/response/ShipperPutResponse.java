package com.nangman.user.application.dto.response;

import com.nangman.user.domain.entity.Shipper;

import java.util.UUID;

public record ShipperPutResponse(
        UUID id
) {
    public static ShipperPutResponse from(Shipper shipper){
        return new ShipperPutResponse(shipper.getId());
    }
}
