package com.nangman.delivery.presentation;

import com.nangman.delivery.application.dto.request.DeliveryPostRequest;
import com.nangman.delivery.application.dto.request.DeliveryPutRequest;
import com.nangman.delivery.application.dto.request.DeliverySearchRequest;
import com.nangman.delivery.application.dto.response.DeliveryResponse;
import com.nangman.delivery.application.service.DeliveryService;
import com.nangman.delivery.common.annotation.RoleCheck;
import com.nangman.delivery.domain.enums.UserRole;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @RoleCheck(role = {UserRole.MASTER})
    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(@Valid @RequestBody DeliveryPostRequest request) {
        DeliveryResponse response = deliveryService.createDelivery(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PagedModel<DeliveryResponse>> searchDelivery(DeliverySearchRequest deliverySearchRequest,
                                                                      @PageableDefault Pageable pageable) {
        Page<DeliveryResponse> response = deliveryService.searchDelivery(deliverySearchRequest, pageable);

        return ResponseEntity.ok(new PagedModel<>(response));
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponse> getDelivery(@PathVariable UUID deliveryId) {
        DeliveryResponse response = deliveryService.getDeliveryById(deliveryId);

        return ResponseEntity.ok(response);
    }

    @RoleCheck(role = {UserRole.MASTER, UserRole.MANAGER, UserRole.SHIPPER})
    @PutMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponse> updateDelivery(@PathVariable UUID deliveryId,
                                                           @Valid @RequestBody DeliveryPutRequest request) {
        DeliveryResponse response = deliveryService.updateDelivery(deliveryId, request);

        return ResponseEntity.ok(response);
    }

    @RoleCheck(role = {UserRole.MASTER, UserRole.MANAGER})
    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable UUID deliveryId,
                                               @RequestHeader("X-User-Id") UUID userId) {
        deliveryService.deleteDeliveryById(deliveryId, userId);

        return ResponseEntity.noContent().build();
    }
}
