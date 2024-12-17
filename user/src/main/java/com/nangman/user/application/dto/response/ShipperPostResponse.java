package com.nangman.user.application.dto.response;

import com.nangman.user.domain.entity.Shipper;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "배송담당자 생성 응답 DTO")
public record ShipperPostResponse(
        @Schema(description = "배송담당자 PK")
        UUID id
) {
    public static ShipperPostResponse from(Shipper shipper){
        return new ShipperPostResponse(shipper.getId());
    }
}
