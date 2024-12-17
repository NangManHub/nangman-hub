package com.nangman.hub.common.config;

import com.nangman.hub.common.interceptor.AuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public AuthRequestInterceptor feignInterceptor() {
        return new AuthRequestInterceptor();
    }
}