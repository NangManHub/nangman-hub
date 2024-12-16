package com.nangman.company.application.dto.response;

import com.nangman.company.domain.entity.Product;
import java.util.UUID;

public record ProductPatchResponse(
        UUID productId,
        String name,
        Integer quantity
) {
    public static ProductPatchResponse from(Product product) {
        return new ProductPatchResponse(
                product.getId(),
                product.getName(),
                product.getQuantity());
    }
}
