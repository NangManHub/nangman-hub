package com.nangman.slack.infrastructure;

import com.nangman.slack.application.dto.UserDto;
import com.nangman.slack.common.config.FeignConfig;
import com.nangman.slack.common.exception.CustomException;
import com.nangman.slack.common.exception.ExceptionType;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user", configuration = FeignConfig.class)
public interface UserClient {

    @CircuitBreaker(name = "userClient", fallbackMethod = "fallbackGetUserById")
    @GetMapping("/users/{userId}")
    UserDto getUserById(@PathVariable(name = "userId") UUID userId);

    default UserDto fallbackGetUserById(UUID userId, Throwable t) {
        throw new CustomException(ExceptionType.USER_NOT_FOUND);
    }
}
