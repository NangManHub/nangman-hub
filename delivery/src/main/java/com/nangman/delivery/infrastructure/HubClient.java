package com.nangman.delivery.infrastructure;

import com.nangman.delivery.application.dto.feign.RouteDetailResponse;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.common.exception.InfraStructureException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="hub")
public interface HubClient {

    @CircuitBreaker(name = "hubClient", fallbackMethod = "getRoutesFallback")
    @GetMapping("/hubs/routes/best?fromHubId={fromHubId}&toHubId={toHubId}")
    List<RouteDetailResponse> getRouts(@PathVariable UUID fromHubId, @PathVariable UUID toHubId);

    default List<RouteDetailResponse> getRoutesFallback(UUID fromHubId, UUID toHubId, Throwable throwable) {
        throw new InfraStructureException(ExceptionStatus.HUB_SERVER_ERROR);
    }
}
