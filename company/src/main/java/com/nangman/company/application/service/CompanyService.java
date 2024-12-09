package com.nangman.company.application.service;

import com.nangman.company.application.dto.request.CompanyPostRequest;
import com.nangman.company.application.dto.response.CompanyPostResponse;
import com.nangman.company.domain.entity.Company;
import com.nangman.company.presentation.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyPostResponse createCompany(CompanyPostRequest request) {
        Company company = companyRepository.save(request.toEntity());
        return CompanyPostResponse.from(company);
    }
}
