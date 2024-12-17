package com.nangman.ai.application.dto.response;

import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        UUID productId,
        String productName,
        Integer productQuantity,
        String requestMessage
) {
}
