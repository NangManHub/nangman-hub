package com.nangman.company.application.dto.response;

import com.nangman.company.domain.entity.Product;
import java.util.UUID;

public record ProductPostResponse(
        UUID productId
) {
    public static ProductPostResponse from(Product product) {
        return new ProductPostResponse(product.getId());
    }
}
