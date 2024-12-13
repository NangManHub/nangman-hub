package com.nangman.company.application.service;

import com.nangman.company.application.dto.CompanyDto;
import com.nangman.company.application.dto.request.CompanyPostRequest;
import com.nangman.company.application.dto.request.CompanyPutRequest;
import com.nangman.company.application.dto.response.CompanyGetResponse;
import com.nangman.company.application.dto.response.CompanyPostResponse;
import com.nangman.company.application.dto.response.CompanyPutResponse;
import com.nangman.company.application.dto.response.CompanySearchGetResponse;
import com.nangman.company.common.feign.HubClient;
import com.nangman.company.common.util.AuthorizationUtils;
import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.enums.CompanyType;
import com.nangman.company.domain.repository.CompanyQueryRepository;
import com.nangman.company.domain.repository.CompanyRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyQueryRepository companyQueryRepository;
    private final AuthorizationUtils authorizationUtils;
    private final HubClient hubClient;

    public CompanyPostResponse createCompany(CompanyPostRequest request) {
        authorizationUtils.validateHubManager(request.hubId());
        Company company = companyRepository.save(request.toEntity());
        return CompanyPostResponse.from(company);
    }

    public CompanyGetResponse getCompany(UUID companyId) {
        Company company = companyRepository.getById(companyId);
        return CompanyGetResponse.from(company);
    }

    @Transactional
    public CompanyPutResponse modifyCompany(UUID companyId, CompanyPutRequest request) {
        Company company = companyRepository.getById(companyId);
        authorizationUtils.validateCompanyAgent(company.getId());
        authorizationUtils.validateHubManager(request.hubId());
        hubClient.getHubById(request.hubId());
        company.updateAll(request);
        return CompanyPutResponse.from(company);
    }

    @Transactional
    public void deleteCompany(UUID companyId) {
        Company company = companyRepository.getById(companyId);
        authorizationUtils.validateHubManager(company.getHubId());
        company.updateIsDeleted(authorizationUtils.getUserIdFromAuthentication());
    }

    public CompanySearchGetResponse searchCompany(String name, UUID hubId, UUID agentId, CompanyType type, String address, Pageable pageable) {
        Page<CompanyDto> companySearchList = companyQueryRepository.searchCompany(name, hubId, agentId, type, address, pageable);
        return CompanySearchGetResponse.from(companySearchList);
    }

}
