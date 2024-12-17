package com.nangman.user.application.dto.kafka;

import java.util.UUID;

public record ShipperEvent(UUID shipperId, ShipperMessage message) {}

