package com.nangman.company.common.feign;

import com.nangman.company.application.dto.UserDto;
import com.nangman.company.common.exception.UserNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user")
public interface UserClient {

    @CircuitBreaker(name = "userClient", fallbackMethod = "fallbackGetUserById")
    @GetMapping("/users/{id}")
    UserDto getUserById(@PathVariable UUID id);

    default UserDto fallbackGetUserById(UUID id, Throwable throwable) {
        throw new UserNotFoundException();
    }
}
