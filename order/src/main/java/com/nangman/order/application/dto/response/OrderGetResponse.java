package com.nangman.order.application.dto.response;

import com.nangman.order.domain.entity.Order;
import java.util.UUID;

public record OrderGetResponse(
        UUID orderId,
        UUID supplierId,
        UUID receiverId,
        UUID productId,
        UUID deliveryId,
        Integer productQuantity,
        String requestMessage
) {
    public static OrderGetResponse from(Order order) {
        return new OrderGetResponse(
                order.getId(),
                order.getSupplierId(),
                order.getReceiverId(),
                order.getProductId(),
                order.getDeliveryId(),
                order.getProductQuantity(),
                order.getRequestMessage());
    }
}
