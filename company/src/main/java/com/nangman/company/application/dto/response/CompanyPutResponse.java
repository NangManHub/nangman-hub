package com.nangman.company.application.dto.response;

import com.nangman.company.application.dto.request.CompanyPutRequest;
import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.enums.CompanyType;
import java.util.UUID;

public record CompanyPutResponse(
        UUID companyId,
        String name,
        UUID hubId,
        UUID agentId,
        CompanyType type,
        String address
) {
    public static CompanyPutResponse from(Company company) {
        return new CompanyPutResponse(
                company.getId(),
                company.getName(),
                company.getHubId(),
                company.getAgentId(),
                company.getType(),
                company.getAddress());
    }
}
