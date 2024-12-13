package com.nangman.company.application.dto.response;

import com.nangman.company.application.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public record ProductSearchGetResponse(
        PagedModel<ProductDto> productList
) {
    public static ProductSearchGetResponse from(Page<ProductDto> searchProductList) {
        return new ProductSearchGetResponse(new PagedModel<>(searchProductList));

    }
}
