package com.nangman.order.application.dto;

import java.util.UUID;

public record ProductDto(
        UUID productId,
        Integer quantity // 남아 있는 상품 수량
) {
}
