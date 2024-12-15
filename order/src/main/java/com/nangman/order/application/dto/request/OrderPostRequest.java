package com.nangman.order.application.dto.request;

import com.nangman.order.domain.entity.Order;
import java.util.UUID;

public record OrderPostRequest(
        UUID supplierId,
        UUID receiverId,
        UUID productId,
        Integer productQuantity,
        String requestMessage
) {
    public Order toEntity() {
        return Order.builder()
                .supplierId(supplierId)
                .receiverId(receiverId)
                .productId(productId)
                .productQuantity(productQuantity)
                .requestMessage(requestMessage)
                .build();
    }
}
