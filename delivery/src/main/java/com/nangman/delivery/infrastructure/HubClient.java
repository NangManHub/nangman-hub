package com.nangman.delivery.infrastructure;

import com.nangman.delivery.application.dto.feign.RouteDto;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.common.exception.InfraStructureException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="hub")
public interface HubClient {

    @CircuitBreaker(name = "hubClient", fallbackMethod = "getRoutesFallback")
    @GetMapping("/hubs/routes?fromHubId={fromHubId}&toHubId={toHubId}")
    RouteDto getRouts(@PathVariable String fromHubId, @PathVariable String toHubId);

    default RouteDto getRoutesFallback(String fromHubId, String toHubId, Throwable throwable) {
        throw new InfraStructureException(ExceptionStatus.HUB_SERVER_ERROR);
    }
}
