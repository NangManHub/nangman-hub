package com.nangman.order.presentation;

import com.nangman.order.application.dto.request.OrderPostRequest;
import com.nangman.order.application.dto.request.OrderPutRequest;
import com.nangman.order.application.dto.response.OrderGetResponse;
import com.nangman.order.application.dto.response.OrderPostResponse;
import com.nangman.order.application.dto.response.OrderSearchGetResponse;
import com.nangman.order.application.service.OrderService;
import com.nangman.order.common.interceptor.Auth;
import com.nangman.order.domain.enums.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성 API", description = "주문을 생성합니다.")
    @Auth(role = {UserRole.MASTER, UserRole.MANAGER, UserRole.AGENT})
    @PostMapping
    public ResponseEntity<OrderPostResponse> createOrder(@RequestBody OrderPostRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @Operation(summary = "주문 조회 API", description = "주문을 조회합니다.")
    @Auth(role = {UserRole.MASTER, UserRole.MANAGER, UserRole.SHIPPER, UserRole.AGENT})
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderGetResponse> getOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @Operation(summary = "주문 수정 API", description = "주문을 수정합니다.")
    @Auth(role = {UserRole.MASTER, UserRole.MANAGER})
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderGetResponse> modifyOrder(@PathVariable UUID orderId,
                                                        @RequestBody OrderPutRequest request) {
        return ResponseEntity.ok(orderService.modifyOrder(orderId, request));
    }

    @Operation(summary = "주문 삭제 API", description = "주문을 삭제합니다.")
    @Auth(role = {UserRole.MASTER, UserRole.MANAGER})
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "주문 검색 API", description = "주문을 검색합니다.")
    @GetMapping
    public ResponseEntity<OrderSearchGetResponse> searchOrder(@RequestParam(required = false) UUID supplierId,
                                                              @RequestParam(required = false) UUID receiverId,
                                                              @RequestParam(required = false) UUID productId,
                                                              @RequestParam(required = false) Integer productQuantity,
                                                              @RequestParam(required = false) String requestMessage,
                                                              @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(orderService.searchOrder(supplierId, receiverId, productId, productQuantity, requestMessage, pageable));
    }

}
