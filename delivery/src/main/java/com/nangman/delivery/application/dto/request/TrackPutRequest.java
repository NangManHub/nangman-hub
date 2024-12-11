package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.TrackStatus;
import java.util.Date;
import java.util.UUID;
import lombok.NonNull;

public record TrackPutRequest(
        @NonNull Integer sequence,
        @NonNull UUID shipperId,
        @NonNull UUID fromHubId,
        UUID toHubId,
        String address,
        @NonNull Integer expectDistance,
        @NonNull Integer expectTime,
        Integer actualDistance,
        Integer actualTime,
        @NonNull TrackStatus status,
        Date departureTime
) {}