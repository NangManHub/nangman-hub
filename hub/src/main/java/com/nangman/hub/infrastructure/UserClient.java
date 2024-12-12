package com.nangman.hub.infrastructure;

import com.nangman.hub.application.dto.UserResponse;
import com.nangman.hub.application.service.UserService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user")
public interface UserClient extends UserService {

    @GetMapping("/users/{userId}")
    UserResponse getUserById(@PathVariable UUID userId);
}
