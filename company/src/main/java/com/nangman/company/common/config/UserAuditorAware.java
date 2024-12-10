package com.nangman.company.common.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuditorAware implements AuditorAware<UUID> {

    private final HttpServletRequest request;

    @Override
    public Optional<UUID> getCurrentAuditor() {
        // TODO: 추후 인가 로직 변경에 따른 수정 필요
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
