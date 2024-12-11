package com.nangman.company.common.interceptor;

import com.nangman.company.domain.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;
        Auth auth = method.getMethodAnnotation(Auth.class);
        if (auth == null) {
            return true;
        }

        String userRoleHeader = request.getHeader("X-User-Role");
        String userIdHeader = request.getHeader("X-User-Id");

        if (userRoleHeader == null || userIdHeader == null) {
            // TODO: Exception 처리
            log.info("User role or ID is required");
        }

        UUID userId = UUID.fromString(userIdHeader);
        UserRole userRole = UserRole.valueOf(userRoleHeader);


        for (UserRole allowedRole : auth.role()) {
            if (allowedRole == userRole) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRoleHeader);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, Collections.singleton(authority));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                return true;
            }
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }
}
