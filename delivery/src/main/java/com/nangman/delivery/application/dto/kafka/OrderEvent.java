package com.nangman.delivery.application.dto.kafka;

import java.util.UUID;

public record OrderEvent(
        UUID fromHubId,
        UUID toHubId,
        String address,
        UUID agentId,
        UUID orderId
) {}