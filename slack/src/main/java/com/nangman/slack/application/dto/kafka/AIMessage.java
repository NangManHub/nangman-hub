package com.nangman.slack.application.dto.kafka;

import java.util.UUID;
public record AIMessage(
        UUID receiverId,
        String message
) {
}
