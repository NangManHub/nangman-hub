package com.nangman.company.common.feign;

import com.nangman.company.application.dto.HubDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub")
public interface HubClient {

    @GetMapping("/hubs/{id}")
    HubDto getHubById(@PathVariable UUID id);

    @GetMapping("/hubs")
    PagedModel<HubDto> getHubByManagerId(@RequestParam UUID managerId);

    // TODO: test 진행 시 fallback method 추가

}
