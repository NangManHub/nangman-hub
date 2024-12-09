package com.nangman.company.application.dto.response;

import com.nangman.company.domain.entity.Company;
import java.util.UUID;

public record CompanyPostResponse(
        UUID companyId
) {
    public static CompanyPostResponse from(Company company) {
        return new CompanyPostResponse(company.getId());
    }
}
