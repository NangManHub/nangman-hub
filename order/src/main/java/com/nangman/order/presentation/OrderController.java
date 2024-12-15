package com.nangman.order.presentation;

import com.nangman.order.application.dto.request.OrderPostRequest;
import com.nangman.order.application.dto.response.OrderGetResponse;
import com.nangman.order.application.dto.response.OrderPostResponse;
import com.nangman.order.application.service.OrderService;
import com.nangman.order.common.interceptor.Auth;
import com.nangman.order.domain.enums.UserRole;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Auth(role = {UserRole.MASTER, UserRole.MANAGER, UserRole.AGENT})
    @PostMapping
    public ResponseEntity<OrderPostResponse> createOrder(@RequestBody OrderPostRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @Auth(role = {UserRole.MASTER, UserRole.MANAGER, UserRole.SHIPPER, UserRole.AGENT})
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderGetResponse> getOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }
}
