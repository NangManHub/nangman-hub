package com.nangman.order.application.dto.response;

import com.nangman.order.application.dto.ProductDto;
import com.nangman.order.domain.entity.Order;
import java.util.UUID;

public record OrderDetailGetResponse(
        UUID orderId,
        UUID productId,
        String productName,
        Integer productQuantity,
        String requestMessage
) {
    public static OrderDetailGetResponse of(Order order, ProductDto product) {
        return new OrderDetailGetResponse(
                order.getId(),
                product.productId(),
                product.name(),
                product.quantity(),
                order.getRequestMessage());
    }
}
