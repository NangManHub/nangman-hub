package com.nangman.slack.application.dto;

import java.util.UUID;

public record HubDto (
        UUID hubId,
        String name,
        String address
)
{}
