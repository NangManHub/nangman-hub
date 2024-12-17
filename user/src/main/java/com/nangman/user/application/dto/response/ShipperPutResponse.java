package com.nangman.user.application.dto.response;

import com.nangman.user.domain.entity.Shipper;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "배송담당자 정보 수정 응답 DTO")
public record ShipperPutResponse(
        @Schema(description = "배송담당자 PK")
        UUID id
) {
    public static ShipperPutResponse from(Shipper shipper){
        return new ShipperPutResponse(shipper.getId());
    }
}
