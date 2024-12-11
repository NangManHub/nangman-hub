package com.nangman.company.common.feign;

import com.nangman.company.application.dto.UserDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserDto getUserById(@PathVariable UUID id);
}
