package com.nangman.order.application.dto.response;

import com.nangman.order.domain.entity.Order;
import java.util.UUID;

public record OrderPostResponse(
    UUID orderId
) {
    public static OrderPostResponse from(Order order) {
        return new OrderPostResponse(order.getId());
    }
}
