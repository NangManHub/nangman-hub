package com.nangman.delivery.application.dto.response;

import com.nangman.delivery.domain.entity.Delivery;
import com.nangman.delivery.domain.entity.DeliveryStatus;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryResponse(
        UUID id,
        DeliveryStatus status,
        UUID fromHubId,
        UUID toHubId,
        String address,
        UUID recipient,
        UUID orderId,
        List<TrackResponse> tracks
) {
    public static DeliveryResponse from(Delivery delivery) {
        return DeliveryResponse.builder()
                .id(delivery.getId())
                .status(delivery.getStatus())
                .fromHubId(delivery.getFromHubId())
                .toHubId(delivery.getToHubId())
                .address(delivery.getAddress())
                .recipient(delivery.getRecipient())
                .orderId(delivery.getOrderId())
                .tracks(delivery.getTracks().stream().map(TrackResponse::from).toList())
                .build();
    }
}