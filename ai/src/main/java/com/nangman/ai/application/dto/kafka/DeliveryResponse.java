package com.nangman.ai.application.dto.kafka;

import com.nangman.ai.application.dto.request.AIPostRequest;
import lombok.Builder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
public record DeliveryResponse(
        UUID id,
        UUID fromHubId,
        UUID toHubId,
        String address,
        UUID recipient,
        UUID orderId,
        List<TrackResponse> tracks
) {
    public AIPostRequest toAIPostRequest() {
        return AIPostRequest.builder()
                .fromHubId(fromHubId)
                .toHubNameList(tracks.stream().map(TrackResponse::toHubName).filter(Objects::nonNull).toList())
                .toHubDurationList(tracks.stream().map(TrackResponse::expectTime).filter(Objects::nonNull).toList())
                .address(address)
                .recipient(recipient)
                .orderId(orderId)
                .shipperId(tracks.get(0).shipperId())
                .build();
    }
}