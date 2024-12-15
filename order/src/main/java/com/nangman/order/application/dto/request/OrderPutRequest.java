package com.nangman.order.application.dto.request;

import jakarta.validation.constraints.Min;
import java.util.UUID;

public record OrderPutRequest(
        UUID supplierId,
        UUID receiverId,
        UUID productId,
        UUID deliveryId,
        @Min(0) Integer productQuantity,
        String requestMessage
) {
}
