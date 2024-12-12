package com.nangman.user.common.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.UUID;

public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();

            // 회원가입, 로그인인 경우 넘어감
            String requestURI = request.getRequestURI();
            if (requestURI.startsWith("/auth/")) {
                return Optional.empty();
            }

            return Optional.of(UUID.fromString(request.getHeader("X-User-Id")));
        }
        return Optional.empty();
    }
}
