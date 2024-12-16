package com.nangman.slack.infrastructure;

import com.nangman.slack.application.dto.feign.HubDto;
import com.nangman.slack.common.exception.CustomException;
import com.nangman.slack.common.exception.ExceptionType;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub")
public interface HubClient {

    @CircuitBreaker(name = "hubClient", fallbackMethod = "fallbackGetHubById")
    @GetMapping("/hubs/{hubId}")
    HubDto getHub(@PathVariable UUID hubId);

    default HubDto fallbackGetHubById(UUID hubId, Throwable t) {
        throw new CustomException(ExceptionType.HUB_NOT_FOUND);
    }
}
