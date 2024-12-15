package com.nangman.order.common.feign;

import com.nangman.order.application.dto.DeliveryDto;
import com.nangman.order.common.exception.CompanyNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DeliveryClient {

    @CircuitBreaker(name = "deliveryClient", fallbackMethod = "fallbackGetDeliveryById")
    @GetMapping("/deliveries/{deliveryId}")
    public DeliveryDto getDeliveryById(@PathVariable UUID deliveryId);

    default DeliveryDto fallbackGetDeliveryById(UUID deliveryId, Throwable throwable) {
        throw new CompanyNotFoundException();
    }

}
