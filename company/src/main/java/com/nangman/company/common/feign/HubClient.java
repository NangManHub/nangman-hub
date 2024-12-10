package com.nangman.company.common.feign;

import com.nangman.company.application.dto.HubDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub")
public interface HubClient {

    @GetMapping("/hubs/{id}")
    HubDto getHubById(@PathVariable UUID id);

    @GetMapping("/hubs/managers/{managerId}")
    HubDto getHubByManagerId(@PathVariable UUID managerId);

    // TODO: test 진행 시 fallback method 추가

}
