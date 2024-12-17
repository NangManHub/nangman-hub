package com.nangman.ai.common.config;

import com.nangman.ai.common.interceptor.AuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public AuthRequestInterceptor feignInterceptor() {
        return new AuthRequestInterceptor();
    }
}