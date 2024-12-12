package com.nangman.company.application.service;

import com.nangman.company.application.dto.request.ProductPostRequest;
import com.nangman.company.application.dto.response.ProductGetResponse;
import com.nangman.company.application.dto.response.ProductPostResponse;
import com.nangman.company.common.util.AuthorizationUtils;
import com.nangman.company.domain.entity.Product;
import com.nangman.company.domain.repository.ProductRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AuthorizationUtils authorizationUtils;

    @Transactional
    public ProductPostResponse createProduct(ProductPostRequest request) {
        authorizationUtils.validateHubManager(request.hubId());
        authorizationUtils.validateCompanyAgent(request.companyId());
        Product product = productRepository.save(request.toEntity());
        return ProductPostResponse.from(product);
    }

    @Transactional(readOnly = true)
    public ProductGetResponse getProduct(UUID productId) {
        Product product = productRepository.getById(productId);
        return ProductGetResponse.from(product);
    }
}
