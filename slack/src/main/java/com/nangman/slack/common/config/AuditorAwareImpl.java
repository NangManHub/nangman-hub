package com.nangman.slack.common.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.UUID;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String userId = request.getHeader("X-User-Id");
            if (userId != null && !userId.isEmpty()) {
                return Optional.of(UUID.fromString(userId));
            }
        }
        return Optional.of(UUID.fromString("00000000-0000-0000-0000-000000000000"));
    }
}

