package com.nangman.order.application.dto.request;

import com.nangman.order.domain.entity.Order;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record OrderPostRequest(

        @NotNull UUID supplierId,
        @NotNull UUID receiverId,
        @NotNull UUID productId,
        @NotNull @Min(0) Integer productQuantity,
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
