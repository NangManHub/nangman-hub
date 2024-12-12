package com.nangman.delivery.common.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {
            String userId = httpRequest.getHeader("X-User-Id");
            String userRole = httpRequest.getHeader("X-User-Role");

            if (userId == null || userRole == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "잘못된 접근입니다.");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}