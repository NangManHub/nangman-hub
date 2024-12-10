package com.nangman.company.application.dto.response;

import com.nangman.company.application.dto.HubDto;
import com.nangman.company.application.dto.UserDto;
import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.enums.CompanyType;
import java.util.UUID;

public record CompanyGetResponse(
        UUID companyId,
        String name,
        HubDto hub,
        CompanyType type,
        UserDto agent
) {
    public static CompanyGetResponse of(Company company, HubDto hub, UserDto agent) {
        return new CompanyGetResponse(
                company.getId(),
                company.getName(),
                hub,
                company.getType(),
                agent
        );
    }
}
