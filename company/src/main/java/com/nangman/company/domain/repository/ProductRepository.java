package com.nangman.company.domain.repository;

import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.entity.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
