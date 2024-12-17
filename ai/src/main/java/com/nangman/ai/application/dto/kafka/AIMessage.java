package com.nangman.ai.application.dto.kafka;

import java.util.UUID;

public record AIMessage(
        UUID receiverId,
        String message
) {
}
