package com.nangman.slack.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public class AuthRequestInterceptor implements RequestInterceptor {

    @Value(value = "${feign.request.auth}")
    private UUID feignReqUserId;

    @Override
    public void apply(RequestTemplate template) {
        template.header("X-User-Id", feignReqUserId.toString());
    }
}