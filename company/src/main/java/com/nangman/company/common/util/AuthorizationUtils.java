package com.nangman.company.common.util;

import com.nangman.company.application.dto.HubDto;
import com.nangman.company.common.exception.AgentMismatchException;
import com.nangman.company.common.exception.HubNotMatchedException;
import com.nangman.company.common.feign.HubClient;
import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.enums.UserRole;
import com.nangman.company.domain.repository.CompanyRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationUtils {

    private final CompanyRepository companyRepository;
    private final HubClient hubClient;

    public void validateHubManager(UUID requestHubId) {
        if (getUserRoleFromAuthentication() == UserRole.MANAGER) {
            PagedModel<HubDto> hubDto = hubClient.getHubByManagerId(getUserIdFromAuthentication());
            UUID hubId = hubDto.getContent().stream()
                    .findFirst()
                    .map(HubDto::hubId)
                    .orElseThrow(HubNotMatchedException::new);
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
