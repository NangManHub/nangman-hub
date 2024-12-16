package com.nangman.order.application.dto;

import com.nangman.order.domain.enums.CompanyType;
import java.util.UUID;

public record CompanyDto(
        UUID companyId,
        String name,
        UUID hubId,
        UUID agentId,
        CompanyType type,
        String address
) {
}
