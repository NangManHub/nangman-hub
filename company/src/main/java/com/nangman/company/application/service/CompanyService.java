package com.nangman.company.application.service;

import com.nangman.company.application.dto.request.CompanyPostRequest;
import com.nangman.company.application.dto.response.CompanyPostResponse;
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
            // UUID hubId = hubClient.getHubId(managerId);
            UUID hubId = UUID.randomUUID();
            if (!request.hubId().equals(hubId)) {
                // TODO: Exception 처리
                log.info("해당 HUB ID는 담당 HUB가 아닙니다.");
            }
        }

        Company company = companyRepository.save(request.toEntity());
        return CompanyPostResponse.from(company);
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
