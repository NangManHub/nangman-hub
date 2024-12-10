package com.nangman.company.presentation;

import com.nangman.company.domain.entity.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
