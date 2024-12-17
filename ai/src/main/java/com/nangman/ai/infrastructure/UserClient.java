package com.nangman.ai.infrastructure;

import com.nangman.ai.application.dto.response.UserResponse;
import com.nangman.ai.common.exception.CustomException;
import com.nangman.ai.common.exception.ExceptionCode;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user")
public interface UserClient {

    @CircuitBreaker(name = "userClient", fallbackMethod = "getUserFallback")
    @GetMapping(value = "/users/{userId}")
    UserResponse getUser(@PathVariable UUID userId);

    default UserResponse getUserFallback(UUID userId, Throwable throwable) {
        throw new CustomException(ExceptionCode.USER_NOT_FOUND, throwable);
    }
}
