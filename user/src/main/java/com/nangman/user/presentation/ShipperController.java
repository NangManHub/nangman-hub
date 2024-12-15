package com.nangman.user.presentation;

import com.nangman.user.application.dto.request.ShipperPostRequest;
import com.nangman.user.application.dto.request.ShipperPutRequest;
import com.nangman.user.application.dto.response.ShipperPostResponse;
import com.nangman.user.application.dto.response.ShipperPutResponse;
import com.nangman.user.application.service.ShipperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Shipper", description = "Shipper API")
@Slf4j
@RestController
@RequestMapping("/shippers")
@RequiredArgsConstructor
public class ShipperController {

    private final ShipperService shipperService;

    @Operation(summary = "배송담당자 생성 API", description = "마스터 또는 담당 허브매니저가 배송담당자를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "배송담당자 생성 성공", content = @Content(schema = @Schema(implementation = ShipperPostResponse.class))),
            @ApiResponse(responseCode = "403", description = "마스터 또는 해당 허브의 매니저만 등록 가능합니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저/배송담당자입니다.")
    })
    @PostMapping
    public ResponseEntity<?> createShipper(@RequestHeader(name = "X-User-Id") UUID reqUserId, @RequestBody ShipperPostRequest shipperPostRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(shipperService.createShipper(reqUserId, shipperPostRequest));
    }

    @Operation(summary = "배송담당자 수정 API", description = "마스터 또는 담당 허브매니저가 배송담당자를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "배송담당자 수정 성공", content = @Content(schema = @Schema(implementation = ShipperPutResponse.class))),
            @ApiResponse(responseCode = "403", description = "마스터 또는 해당 허브의 매니저만 수정 가능합니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저/배송담당자입니다.")
    })
    @PutMapping("/{shipperId}")
    public ResponseEntity<?> updateShipper(@RequestHeader(name = "X-User-Id") UUID reqUserId,
                                           @PathVariable UUID shipperId,
                                           @RequestBody ShipperPutRequest shipperPutRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(shipperService.updateShipper(reqUserId, shipperId, shipperPutRequest));
    }

    @Operation(summary = "배송담당자 삭제 API", description = "마스터 또는 담당 허브매니저가 배송담당자를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "배송담당자 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "마스터 또는 해당 허브의 매니저만 삭제 가능합니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저/배송담당자입니다.")
    })
    @DeleteMapping("/{shipperId}")
    public ResponseEntity<?> deleteShipper(@RequestHeader(name = "X-User-Id") UUID reqUserId, @PathVariable UUID shipperId) {

        shipperService.deleteShipper(reqUserId, shipperId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
