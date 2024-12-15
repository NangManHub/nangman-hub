package com.nangman.order.common.util;

import com.nangman.order.application.dto.CompanyDto;
import com.nangman.order.application.dto.DeliveryDto;
import com.nangman.order.application.dto.HubDto;
import com.nangman.order.application.dto.TrackDto;
import com.nangman.order.common.exception.AgentMismatchException;
import com.nangman.order.common.exception.HubNotMatchedException;
import com.nangman.order.common.exception.ShipperMismatchException;
import com.nangman.order.common.feign.CompanyClient;
import com.nangman.order.common.feign.DeliveryClient;
import com.nangman.order.common.feign.HubClient;
import com.nangman.order.domain.enums.UserRole;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationUtils {

    private final CompanyClient companyClient;
    private final HubClient hubClient;
    private final DeliveryClient deliveryClient;

    // TODO: API 속도 측정 후 캐싱 적용
    public void validateHubManager(UUID requestCompanyId) {
        if (getUserRoleFromAuthentication() == UserRole.MANAGER) {
            CompanyDto companyDto = companyClient.getCompanyById(requestCompanyId);
            HubDto hubDto = hubClient.getHubById(companyDto.hubId());
            if (!hubDto.managerId().equals(getUserIdFromAuthentication())) {
                throw new HubNotMatchedException();
            }
        }
    }

    public void validateCompanyAgent(UUID requestCompanyId) {
        if (getUserRoleFromAuthentication() == UserRole.AGENT) {
            CompanyDto company = companyClient.getByAgentId(getUserIdFromAuthentication());
            if (!company.companyId().equals(requestCompanyId)) {
                throw new AgentMismatchException();
            }
        }
    }

    public void validateDeliveryShipper(UUID requestDeliveryId) {
        if (getUserRoleFromAuthentication() == UserRole.SHIPPER) {
            DeliveryDto delivery = deliveryClient.getDeliveryById(requestDeliveryId);
            UUID requestShipperId = getUserIdFromAuthentication();

            boolean isValidShipper = delivery.tracks().stream()
                    .map(TrackDto::shipperId)
                    .anyMatch(shipperId -> shipperId.equals(requestShipperId));

            if (!isValidShipper) {
                throw new ShipperMismatchException();
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
