package com.nangman.ai.application.dto.kafka;

import java.util.UUID;

public record AIEvent(
        UUID aiId,
        AIMessage message
) {
}
