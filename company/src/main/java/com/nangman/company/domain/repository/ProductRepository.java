package com.nangman.company.domain.repository;

import com.nangman.company.common.exception.ProductNotFoundException;
import com.nangman.company.domain.entity.Product;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByIdAndIsDeleteFalse(UUID productId);

    default Product getById(UUID productId) {
        return findByIdAndIsDeleteFalse(productId)
                .orElseThrow(ProductNotFoundException::new);
    }
}
