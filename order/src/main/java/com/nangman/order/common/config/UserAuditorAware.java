package com.nangman.order.common.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuditorAware implements AuditorAware<UUID> {

    private final HttpServletRequest request;

    @Override
    public Optional<UUID> getCurrentAuditor() {
        String userIdHeader = request.getHeader("X-User-Id");
        if (userIdHeader != null) {
            try {
                UUID userId = UUID.fromString(userIdHeader);
                return Optional.of(userId);
            } catch (IllegalArgumentException e) {
                log.info("Invalid UUID in X-User-Id header: " + userIdHeader);
            }
        }
        return Optional.empty();
    }
}
