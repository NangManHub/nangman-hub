package com.nangman.ai.infrastructure;

import com.nangman.ai.application.dto.response.OrderResponse;
import com.nangman.ai.common.exception.CustomException;
import com.nangman.ai.common.exception.ExceptionCode;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "order")
public interface OrderClient {

    @CircuitBreaker(name = "orderClient", fallbackMethod = "getOrderFallback")
    @GetMapping("/orders/ai/{orderId}")
    OrderResponse getOrder(@PathVariable UUID orderId);

    default OrderResponse getOrderFallback(UUID orderId, Throwable throwable) {
        throw new CustomException(ExceptionCode.ORDER_NOT_FOUND, throwable);
    }
}
