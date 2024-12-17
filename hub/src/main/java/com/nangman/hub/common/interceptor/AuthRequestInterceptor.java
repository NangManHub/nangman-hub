package com.nangman.hub.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public class AuthRequestInterceptor implements RequestInterceptor {

    @Value(value = "${service.auth.request.userId}")
    private UUID authReqUserId;

    @Override
    public void apply(RequestTemplate template) {
        template.header("X-User-Id", authReqUserId.toString());
    }
}
