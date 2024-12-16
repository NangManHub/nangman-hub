package com.nangman.order.common.config;

import com.nangman.order.common.util.AuthorizationUtils;
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

    private final AuthorizationUtils authorizationUtils;

    @Override
    public Optional<UUID> getCurrentAuditor() {
        if (authorizationUtils.getUserIdFromAuthentication() != null) {
            return Optional.of(authorizationUtils.getUserIdFromAuthentication());
        } else {
            // kafka에서 넘어온 요청일 경우, 마스터 UUID 저장
            return Optional.of(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        }
    }
}
