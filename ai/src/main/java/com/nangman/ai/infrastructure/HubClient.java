package com.nangman.ai.infrastructure;

import com.nangman.ai.application.dto.response.HubResponse;
import com.nangman.ai.common.exception.CustomException;
import com.nangman.ai.common.exception.ExceptionCode;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub")
public interface HubClient {

    @CircuitBreaker(name = "hubClient", fallbackMethod = "getHubFallback")
    @GetMapping("/hubs/{hubId}")
    HubResponse getHub(@PathVariable UUID hubId);

    default HubResponse getHubFallback(UUID hubId, Throwable throwable) {
        throw new CustomException(ExceptionCode.HUB_NOT_FOUND, throwable);
    }
}
