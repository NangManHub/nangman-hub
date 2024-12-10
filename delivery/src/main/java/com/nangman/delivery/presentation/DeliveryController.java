package com.nangman.delivery.presentation;

import com.nangman.delivery.application.dto.request.DeliveryPostRequest;
import com.nangman.delivery.application.dto.response.DeliveryResponse;
import com.nangman.delivery.application.service.DeliveryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(@RequestBody DeliveryPostRequest request) {
        DeliveryResponse response = deliveryService.createDelivery(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponse> getDelivery(@PathVariable UUID deliveryId) {
        DeliveryResponse response = deliveryService.getDeliveryByDeliveryId(deliveryId);

        return ResponseEntity.ok(response);
    }
}
