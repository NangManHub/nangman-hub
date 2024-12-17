package com.nangman.ai.application.dto.request;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record AIPostRequest(
        UUID fromHubId,
        List<String> toHubNameList,
        List<Integer> toHubDurationList,
        String address,
        UUID recipient,
        UUID orderId,
        UUID shipperId
) {
}
