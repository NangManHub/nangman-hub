package com.nangman.company.application.dto.response;

import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.enums.CompanyType;
import java.util.UUID;

public record CompanyGetResponse(
        UUID companyId,
        String name,
        UUID hubId,
        CompanyType type,
        UUID agentId
) {
    public static CompanyGetResponse from(Company company) {
        return new CompanyGetResponse(
                company.getId(),
                company.getName(),
                company.getHubId(),
                company.getType(),
                company.getAgentId()
        );
    }
}
