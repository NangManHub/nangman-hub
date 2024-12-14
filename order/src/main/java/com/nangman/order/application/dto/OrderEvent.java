package com.nangman.order.application.dto;

import com.nangman.order.domain.entity.Order;
import java.util.UUID;

public record OrderEvent(
        UUID orderId,
        UUID supplierId,
        UUID receiverId
) {
    public static OrderEvent from(Order order) {
        return new OrderEvent(
                order.getId(),
                order.getSupplierId(),
                order.getReceiverId()
        );
    }
}
