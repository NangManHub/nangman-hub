package com.nangman.company.application.dto.response;

import com.nangman.company.application.dto.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public record CompanySearchGetResponse(
        PagedModel<CompanyDto> companyList
) {
    public static CompanySearchGetResponse from(Page<CompanyDto> searchCompanyList) {
        return new CompanySearchGetResponse(new PagedModel<>(searchCompanyList));
    }
}
