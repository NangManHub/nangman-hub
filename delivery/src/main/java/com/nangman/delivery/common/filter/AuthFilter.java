package com.nangman.delivery.common.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter implements Filter {

    @Value("${service.gateway.secret-key}")
    private String gatewaySecretKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {
            if(httpRequest.getRequestURI().startsWith("/deliveries/swagger") || httpRequest.getRequestURI().startsWith("/deliveries/v3/api-docs")){
                chain.doFilter(request, response);
                return;
            }

            String userId = httpRequest.getHeader("X-User-Id");
            String userRole = httpRequest.getHeader("X-User-Role");
            String gateWaySecret = httpRequest.getHeader("X-Gateway-Secret");

//            if(!gatewaySecretKey.equals(gateWaySecret)){
//                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "잘못된 접근입니다.");
//                return;
//            }

            if (userId == null || userRole == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}