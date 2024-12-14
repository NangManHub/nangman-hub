package com.nangman.order.common.feign;

import com.nangman.order.application.dto.CompanyDto;
import com.nangman.order.common.exception.CompanyNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "company")
public interface CompanyClient {

    @CircuitBreaker(name = "companyClient", fallbackMethod = "fallbackGetCompanyById")
    @GetMapping("/companies/{companyId}")
    CompanyDto getCompanyById(@PathVariable UUID companyId);

    @CircuitBreaker(name = "companyClient", fallbackMethod = "fallbackGetCompanyByAgentId")
    @GetMapping("/companies")
    CompanyDto getByAgentId(@RequestParam UUID agentId);

    default CompanyDto fallbackGetCompanyByAgentId(UUID agentId, Throwable throwable) {
        throw new CompanyNotFoundException();
    }

    default CompanyDto fallbackGetCompanyById(UUID companyId, Throwable throwable) {
        throw new CompanyNotFoundException();
    }
}
