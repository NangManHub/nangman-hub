package com.nangman.company.presentation;

import com.nangman.company.application.dto.request.ProductPostRequest;
import com.nangman.company.application.dto.response.ProductPostResponse;
import com.nangman.company.application.service.ProductService;
import com.nangman.company.common.interceptor.Auth;
import com.nangman.company.domain.enums.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Auth(role = {UserRole.MASTER, UserRole.MANAGER, UserRole.AGENT})
    @PostMapping
    public ResponseEntity<ProductPostResponse> createProduct(
            @Valid @RequestBody ProductPostRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

}
