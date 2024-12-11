package com.nangman.company.application.dto;

import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.enums.CompanyType;
import java.util.UUID;

public record CompanyDto(
        UUID companyId,
        String name,
        UUID hubId,
        UUID agentId,
        CompanyType type,
        String address
) {
    public static CompanyDto from(Company company) {
        return new CompanyDto(
                company.getId(),
                company.getName(),
                company.getHubId(),
                company.getAgentId(),
                company.getType(),
                company.getAddress()
        );
    }
}
