package com.nangman.company.application.dto.request;

import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record ProductPostRequest(
        UUID hubId,
        UUID companyId,
        @Size(min = 1, max = 20)
        String name,
        @Min(0)
        Integer quantity
) {
    public Product toEntity(Company company) {
        return Product.builder()
                .hubId(hubId)
                .name(name)
                .company(company)
                .quantity(quantity)
                .build();
    }
}
