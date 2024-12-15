package com.nangman.delivery.infrastructure;

import com.nangman.delivery.domain.entity.Shipper;
import com.nangman.delivery.domain.enums.ShipperType;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final ZSetOperations<String, UUID> zSetOperations;

    public void addShipperZSet(Shipper shipper, double score) {
        zSetOperations.add(generateKey(shipper.getShipperType(), shipper.getHubId()), shipper.getId(), score);
    }

    public UUID getShipperZSet(Shipper shipper, long distance) {
        String key = generateKey(shipper.getShipperType(), shipper.getHubId());
        Set<UUID> shippers = zSetOperations.rangeByScore(key, Long.MIN_VALUE, Long.MAX_VALUE, 0, 1);

        if (shippers != null && !shippers.isEmpty()) {
            UUID shipperId = shippers.iterator().next();
            zSetOperations.incrementScore(key, shipperId, distance);
            return shipperId;
        }
        return null;
    }

    public void completeTask(Shipper shipper, long distance) {
        String key = generateKey(shipper.getShipperType(), shipper.getHubId());
        zSetOperations.incrementScore(key, shipper.getId(), -distance);
    }

    public void deleteShipperZSet(Shipper shipper) {
        zSetOperations.remove(generateKey(shipper.getShipperType(), shipper.getHubId()), shipper.getId());
    }

    /**
     * Redis Key 생성
     */
    private String generateKey(ShipperType shipperType, UUID hubId) {
        return String.format("shipperQueue:%s:%s", shipperType.name(), hubId.toString());
    }
}
