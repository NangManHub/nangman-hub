package com.nangman.company.presentation;

import com.nangman.company.common.exception.CompanyNotFoundException;
import com.nangman.company.domain.entity.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    default Company getById(UUID companyId) {
        return findById(companyId).orElseThrow(CompanyNotFoundException::new);
    }
}
