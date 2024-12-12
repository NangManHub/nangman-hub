package com.nangman.user.common.feign;

import com.nangman.user.application.dto.HubDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub")
public interface HubClient {

    @GetMapping("/hubs/managers/{managerId}")
    HubDto getHubByManagerId (@PathVariable UUID managerId);

}
