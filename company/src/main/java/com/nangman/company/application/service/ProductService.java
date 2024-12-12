package com.nangman.company.application.service;

import com.nangman.company.application.dto.ProductDto;
import com.nangman.company.application.dto.request.ProductPostRequest;
import com.nangman.company.application.dto.response.ProductGetResponse;
import com.nangman.company.application.dto.response.ProductPostResponse;
import com.nangman.company.application.dto.response.ProductSearchGetResponse;
import com.nangman.company.common.util.AuthorizationUtils;
import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.entity.Product;
import com.nangman.company.domain.repository.CompanyRepository;
import com.nangman.company.domain.repository.ProductQueryRepository;
import com.nangman.company.domain.repository.ProductRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductQueryRepository productQueryRepository;
    private final AuthorizationUtils authorizationUtils;

    @Transactional
    public ProductPostResponse createProduct(ProductPostRequest request) {
        authorizationUtils.validateHubManager(request.hubId());
        authorizationUtils.validateCompanyAgent(request.companyId());
        Company company = companyRepository.getById(request.companyId());
        Product product = productRepository.save(request.toEntity(company));
        return ProductPostResponse.from(product);
    }

    @Transactional(readOnly = true)
    public ProductGetResponse getProduct(UUID productId) {
        Product product = productRepository.getById(productId);
        return ProductGetResponse.from(product);
    }

    @Transactional
    public ProductGetResponse modifyProduct(UUID productId, ProductPostRequest request) {
        Product product = productRepository.getById(productId);
        authorizationUtils.validateHubManager(request.hubId());
        authorizationUtils.validateCompanyAgent(request.companyId());
        Company company = companyRepository.getById(request.companyId());
        product.updateAll(request.hubId(), company, request.name(), request.quantity());
        return ProductGetResponse.from(product);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        Product product = productRepository.getById(productId);
        authorizationUtils.validateHubManager(product.getHubId());
        authorizationUtils.validateCompanyAgent(product.getCompany().getId());
        product.updateIsDeleted(authorizationUtils.getUserIdFromAuthentication());
    }

    public ProductSearchGetResponse searchProduct(String name, UUID hubId, UUID companyId, Integer quantity, Pageable pageable) {
        Page<ProductDto> searchProductList = productQueryRepository.searchProduct(name, hubId, companyId, quantity, pageable);
        return ProductSearchGetResponse.from(searchProductList);
    }
}
