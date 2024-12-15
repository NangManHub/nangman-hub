package com.nangman.delivery.domain.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final ListOperations<UUID, UUID> listOperations;

    public void addShipperQueue(UUID hubId, UUID shipperId) {
        listOperations.leftPush(hubId, shipperId);
    }

    public UUID getShipperQueue(UUID hubId) {
        UUID shipperId = listOperations.rightPop(hubId);
        if (shipperId != null) {
            addShipperQueue(hubId, shipperId);
        }
        return shipperId;
    }
}
