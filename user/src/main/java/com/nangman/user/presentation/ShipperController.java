package com.nangman.user.presentation;

import com.nangman.user.application.dto.request.ShipperPostRequest;
import com.nangman.user.application.dto.request.ShipperPutRequest;
import com.nangman.user.application.service.ShipperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/shippers")
@RequiredArgsConstructor
public class ShipperController {

    private final ShipperService shipperService;

    @PostMapping
    public ResponseEntity<?> createShipper(@RequestHeader(name = "X-User-Id") UUID reqUserId, @RequestBody ShipperPostRequest shipperPostRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(shipperService.createShipper(reqUserId, shipperPostRequest));
    }

    @PutMapping("/{shipperId}")
    public ResponseEntity<?> updateShipper(@RequestHeader(name = "X-User-Id") UUID reqUserId,
                                           @PathVariable UUID shipperId,
                                           @RequestBody ShipperPutRequest shipperPutRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(shipperService.updateShipper(reqUserId, shipperId, shipperPutRequest));
    }

    @DeleteMapping("/{shipperId}")
    public ResponseEntity<?> deleteShipper(@RequestHeader(name = "X-User-Id") UUID reqUserId, @PathVariable UUID shipperId) {

        shipperService.deleteShipper(reqUserId, shipperId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
