package com.nangman.company.application.service;

import com.nangman.company.application.dto.CompanyDto;
import com.nangman.company.application.dto.HubDto;
import com.nangman.company.application.dto.UserDto;
import com.nangman.company.application.dto.request.CompanyPostRequest;
import com.nangman.company.application.dto.request.CompanyPutRequest;
import com.nangman.company.application.dto.response.CompanyGetResponse;
import com.nangman.company.application.dto.response.CompanyPostResponse;
import com.nangman.company.application.dto.response.CompanyPutResponse;
import com.nangman.company.application.dto.response.CompanySearchGetResponse;
import com.nangman.company.common.exception.AgentMismatchException;
import com.nangman.company.common.exception.HubNotMatchedException;
import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.enums.CompanyType;
import com.nangman.company.domain.enums.UserRole;
import com.nangman.company.domain.repository.CompanyQueryRepository;
import com.nangman.company.domain.repository.CompanyRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyQueryRepository companyQueryRepository;

    public CompanyPostResponse createCompany(CompanyPostRequest request) {
        // TODO: UserRole이 MANAGER면 request hub ID와 담당자의 hub ID가 같은 지 확인
        if (getUserRoleFromAuthentication() == UserRole.MANAGER) {
            // UUID hubId = hubClient.getHubByManagerId(managerId);
            UUID hubId = UUID.randomUUID();
            if (!request.hubId().equals(hubId)) {
                throw new HubNotMatchedException();
            }
        }

        Company company = companyRepository.save(request.toEntity());
        return CompanyPostResponse.from(company);
    }

    public CompanyGetResponse getCompany(UUID companyId) {
        Company company = companyRepository.getById(companyId);
        // TODO: Hub, Agent 가져오는 Feign Client 개발
        // HubDto hub = hubClient.getHubById(company.getHubId());
        // UserDto agent = userClient.getUserById(company.getAgentId());
        return CompanyGetResponse.of(company,
                new HubDto(UUID.randomUUID(), "name", "address", UserDto.createTmpUser()),
                UserDto.createTmpUser());
    }

    @Transactional
    public CompanyPutResponse modifyCompany(UUID companyId, CompanyPutRequest request) {
        Company company = companyRepository.getById(companyId);

        if (getUserRoleFromAuthentication() == UserRole.AGENT) {
            if (!Objects.equals(getUserIdFromAuthentication(), company.getAgentId())) {
                throw new AgentMismatchException();
            }
        }

        // TODO: UserRole이 MANAGER면 담당 허브의 업체만 수정 가능
        else if (getUserRoleFromAuthentication() == UserRole.MANAGER) {
            // UUID hubId = hubClient.getHubByManagerId(managerId);
            UUID hubId = UUID.randomUUID();
            if (!request.hubId().equals(hubId)) {
                throw new HubNotMatchedException();
            }
        }

        // TODO: HUB 수정 시 존재하는 HUB인지 검증 필요 -> HubClient
        // TODO: AGENT 수정 시 request agentId가 AGENT role 가진 유저인지 검증 필요 -> UserClient

        company.updateAll(request);
        return CompanyPutResponse.from(company);
    }

    @Transactional
    public void deleteCompany(UUID companyId) {
        Company company = companyRepository.getById(companyId);
        // TODO: MANAGER는 담당 HUB의 업체만 삭제 가능 -> HubClient
        if (getUserRoleFromAuthentication() == UserRole.MANAGER) {
            // UUID hubId = hubClient.getHubByManagerId(managerId);
            UUID hubId = UUID.randomUUID();
            if(!company.getHubId().equals(hubId)) {
                throw new HubNotMatchedException();
            }
        }

        company.updateIsDeleted(getUserIdFromAuthentication());
    }

    public CompanySearchGetResponse searchCompany(String name, UUID hubId, UUID agentId, CompanyType type, String address, Pageable pageable) {
        Page<CompanyDto> companySearchList = companyQueryRepository.searchCompany(name, hubId, agentId, type, address, pageable);
        return CompanySearchGetResponse.from(companySearchList);
    }

    private UUID getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            return null;
        }
        return UUID.fromString(authentication.getPrincipal().toString());
    }

    private UserRole getUserRoleFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            return null;
        }
        return UserRole.valueOf(authentication.getAuthorities().iterator().next().getAuthority());
    }
}
