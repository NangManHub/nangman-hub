package com.nangman.delivery.application.dto.request;

import com.nangman.delivery.domain.entity.TrackStatus;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;
import lombok.NonNull;

public record TrackPutRequest(
        @NotNull Integer sequence,
        @NotNull UUID shipperId,
        @NotNull UUID fromHubId,
        UUID toHubId,
        String address,
        @NotNull Integer expectDistance,
        @NotNull Integer expectTime,
        Integer actualDistance,
        Integer actualTime,
        @NotNull TrackStatus status,
        Date departureTime
) {}