package com.nangman.company.common.feign;

import com.nangman.company.application.dto.HubDto;
import com.nangman.company.common.exception.HubNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub")
public interface HubClient {

    @CircuitBreaker(name = "hubClient", fallbackMethod = "fallbackGetHubById")
    @GetMapping("/hubs/{id}")
    HubDto getHubById(@PathVariable UUID id);

    @CircuitBreaker(name = "hubClient", fallbackMethod = "fallbackGetHubByManagerId")
    @GetMapping("/hubs")
    PagedModel<HubDto> getHubByManagerId(@RequestParam UUID managerId);

    default HubDto fallbackGetHubById(UUID id, Throwable throwable) {
        throw new HubNotFoundException();
    }

    default PagedModel<HubDto> fallbackGetHubByManagerId(UUID managerId, Throwable throwable) {
        throw new HubNotFoundException();
    }

}
