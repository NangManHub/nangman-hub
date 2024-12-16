package com.nangman.slack.application.dto.feign;

import java.util.UUID;

public record HubDto (
        UUID hubId,
        String name,
        String address
)
{}
