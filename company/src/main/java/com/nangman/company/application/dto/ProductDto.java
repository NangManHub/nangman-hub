package com.nangman.company.application.dto;

import com.nangman.company.domain.entity.Product;
import java.util.UUID;

public record ProductDto(
        UUID productId,
        String name,
        UUID hubId,
        UUID companyId,
        Integer quantity
) {
    public static ProductDto from(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getHubId(),
                product.getCompany().getId(),
                product.getQuantity());
    }
}
