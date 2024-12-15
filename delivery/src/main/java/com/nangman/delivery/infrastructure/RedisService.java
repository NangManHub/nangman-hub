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

    private final ZSetOperations<String, String> zSetOperations;

    public void addShipperZSet(Shipper shipper, double score) {
        zSetOperations.add(generateKey(shipper.getShipperType(), shipper.getHubId()), shipper.getId().toString(), score);
    }

    public UUID getShipperZSet(ShipperType shipperType, UUID hubId, long distance) {
        String key = generateKey(shipperType, hubId);
        Set<String> shippers = zSetOperations.rangeByScore(key, Long.MIN_VALUE, Long.MAX_VALUE, 0, 1);

        if (shippers != null && !shippers.isEmpty()) {
            String shipperIdString = shippers.iterator().next();
            UUID shipperId = UUID.fromString(shipperIdString);
            zSetOperations.incrementScore(key, shipperIdString, distance);
            return shipperId;
        }
        return null;
    }

    public void completeTask(Shipper shipper, long distance) {
        String key = generateKey(shipper.getShipperType(), shipper.getHubId());
        zSetOperations.incrementScore(key, shipper.getId().toString(), -distance);
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
