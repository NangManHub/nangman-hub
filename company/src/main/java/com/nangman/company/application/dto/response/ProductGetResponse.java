package com.nangman.company.application.dto.response;

import com.nangman.company.domain.entity.Product;
import java.util.UUID;

public record ProductGetResponse(
        UUID productId,
        String name,
        UUID hubId,
        UUID companyId,
        Integer quantity
) {
    public static ProductGetResponse from(Product product) {
        return new ProductGetResponse(
                product.getId(),
                product.getName(),
                product.getHubId(),
                product.getCompanyId(),
                product.getQuantity());
    }
}
