package com.nangman.gateway.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class LocalJwtAuthenticationFilter implements GlobalFilter {

    @Value("${service.jwt.secret-key}")
    private String secretKey;

    @Value("${service.gateway.secret-key}")
    private String gatewayKey;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // "/auth"로 시작하는 경로는 필터를 건너뛰고 그대로 전달
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        // JWT 토큰 추출
        String token = extractToken(exchange);

        // 토큰 유효성 검사 및 클레임 추출
        Claims payload = validateToken(token);

        // 토큰이 없거나 유효하지 않으면 401 UNAUTHORIZED 응답 반환
        if(token == null || payload == null){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 유효한 토큰의 클레임을 요청 헤더에 추가
        ServerHttpRequest build = exchange.getRequest().mutate()
                .header("X-User-Id", payload.getSubject())
                .header("X-Username", payload.get("username").toString())
                .header("X-User-Role", payload.get("role").toString())
                .header("X-Gateway-Key", gatewayKey)
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate().request(build).build();

        // 다음 필터 체인으로 요청 전달
        return chain.filter(mutatedExchange);
    }

    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(7);
        }
        return null;
    }

    private Claims validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return claimsJws.getPayload();

        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return null;
    }

}
