package com.nangman.order.application.dto.event;

import com.nangman.order.application.dto.CompanyDto;
import com.nangman.order.domain.entity.Order;
import java.util.UUID;

public record OrderEvent(
        UUID fromHubId,
        UUID toHubId,
        String address,
        UUID agentId,
        UUID orderId
) {
    public static OrderEvent from(Order order, CompanyDto supplier, CompanyDto receiver) {
        return new OrderEvent(
                supplier.hubId(),
                receiver.hubId(),
                receiver.address(),
                receiver.agentId(),
                order.getId());
    }
}
