package com.nangman.delivery.common.aspects;

import com.nangman.delivery.common.annotation.RoleCheck;
import com.nangman.delivery.common.exception.AuthException;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.domain.enums.UserRole;
import com.nangman.delivery.domain.repository.DeliveryRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@RequiredArgsConstructor
public class AuthAspect {

    private final HttpServletRequest request;
    private final DeliveryRepository deliveryRepository;

    @Before("@annotation(roleCheck)")
    public void checkRole(JoinPoint joinPoint, RoleCheck roleCheck) {
        UserRole[] allowedRoles = roleCheck.role();
        String userRole = request.getHeader("X-User-Role");

        if (Arrays.stream(allowedRoles).noneMatch(role -> role.name().equals(userRole))) {
            throw new AuthException(ExceptionStatus.AUTHORIZATION_FAILED);
        }
    }
}
