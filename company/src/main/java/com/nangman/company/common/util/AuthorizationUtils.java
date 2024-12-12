package com.nangman.company.common.util;

import com.nangman.company.application.service.CompanyService;
import com.nangman.company.common.exception.AgentMismatchException;
import com.nangman.company.common.exception.HubNotMatchedException;
import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.enums.UserRole;
import com.nangman.company.domain.repository.CompanyRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationUtils {

    private final CompanyRepository companyRepository;

    public void validateHubManager(UUID requestHubId) {
        // TODO: 허브 담당자 ID로 담당 HUB 찾아와서 비교
        if (getUserRoleFromAuthentication() == UserRole.MANAGER) {
            // UUID hubId = hubClient.getHubByManagerId(managerId);
            UUID hubId = UUID.randomUUID();
            if (!requestHubId.equals(hubId)) {
                throw new HubNotMatchedException();
            }
        }
    }

    public void validateCompanyAgent(UUID requestCompanyId) {
        if (getUserRoleFromAuthentication() == UserRole.AGENT) {
            Company company = companyRepository.getByAgentId(getUserIdFromAuthentication());
            if (!company.getId().equals(requestCompanyId)) {
                throw new AgentMismatchException();
            }
        }
    }

    public UUID getUserIdFromAuthentication() {
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
