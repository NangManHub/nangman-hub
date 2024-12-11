package com.nangman.company.domain.repository;

import com.nangman.company.common.exception.AgentMismatchException;
import com.nangman.company.common.exception.CompanyNotFoundException;
import com.nangman.company.domain.entity.Company;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByAgentId(UUID agentId);

    default Company getById(UUID companyId) {
        return findById(companyId).orElseThrow(CompanyNotFoundException::new);
    }

    default Company getByAgentId(UUID agentId) {
        return findByAgentId(agentId).orElseThrow(CompanyNotFoundException::new);
    };
}
