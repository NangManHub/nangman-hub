package com.nangman.company.application.service;

import com.nangman.company.application.dto.HubDto;
import com.nangman.company.application.dto.UserDto;
import com.nangman.company.application.dto.request.CompanyPostRequest;
import com.nangman.company.application.dto.response.CompanyGetResponse;
import com.nangman.company.application.dto.response.CompanyPostResponse;
import com.nangman.company.common.exception.ExceptionStatus;
import com.nangman.company.common.exception.HubNotMatchedException;
import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.enums.UserRole;
import com.nangman.company.presentation.CompanyRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

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
