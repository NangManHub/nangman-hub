package com.nangman.company.application.dto.request;

import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.enums.CompanyType;
import java.util.UUID;

public record CompanyPostRequest(
        UUID hubId,
        UUID agentId,
        String name,
        CompanyType type,
        String address
) {
    public Company toEntity() {
        return Company.builder()
                .hubId(hubId)
                .agentId(agentId)
                .name(name)
                .type(type)
                .address(address)
                .build();
    }
}
