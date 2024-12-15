package com.nangman.company.presentation;

import com.nangman.company.application.dto.response.ProductPatchResponse;
import com.nangman.company.application.dto.response.ProductSearchGetResponse;
import com.nangman.company.application.dto.request.ProductPostRequest;
import com.nangman.company.application.dto.response.ProductGetResponse;
import com.nangman.company.application.dto.response.ProductPostResponse;
import com.nangman.company.application.service.ProductService;
import com.nangman.company.common.interceptor.Auth;
import com.nangman.company.domain.enums.UserRole;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{productId}")
    public ResponseEntity<ProductGetResponse> getProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @Auth(role = {UserRole.MASTER, UserRole.MANAGER, UserRole.AGENT})
    @PutMapping("/{productId}")
    public ResponseEntity<ProductGetResponse> modifyProduct(@PathVariable UUID productId,
                                                            @Valid @RequestBody ProductPostRequest request) {
        return ResponseEntity.ok(productService.modifyProduct(productId, request));
    }

    @Auth(role = {UserRole.MASTER, UserRole.MANAGER, UserRole.AGENT})
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ProductSearchGetResponse> searchCompany(@RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) UUID hubId,
                                                                  @RequestParam(required = false) UUID companyId,
                                                                  @RequestParam(required = false) Integer quantity,
                                                                  @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(productService.searchProduct(name, hubId, companyId, quantity, pageable));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductPatchResponse> checkProductQuantity(@PathVariable UUID productId,
                                                                     @RequestParam Integer quantity) {
        return ResponseEntity.ok(productService.checkProductQuantity(productId, quantity));
    }
    
}
