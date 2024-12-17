package com.nangman.company.application.dto.request;

import com.nangman.company.domain.enums.CompanyType;
import java.util.UUID;

public record CompanyPutRequest(
        String name,
        UUID hubId,
        UUID agentId,
        CompanyType type,
        String address
) {
}
