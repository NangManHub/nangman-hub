package com.nangman.order.application.dto;

import com.nangman.order.domain.entity.Order;
import java.util.UUID;

public record OrderDto(
        UUID orderId,
        UUID supplierId,
        UUID receiverId,
        UUID productId,
        Integer productQuantity,
        String requestMessage
) {
    public static OrderDto from(Order order) {
        return new OrderDto(
                order.getId(),
                order.getSupplierId(),
                order.getReceiverId(),
                order.getProductId(),
                order.getProductQuantity(),
                order.getRequestMessage());
    }
}
