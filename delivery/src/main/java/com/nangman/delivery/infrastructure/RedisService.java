package com.nangman.delivery.infrastructure;

import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.common.exception.InfraStructureException;
import com.nangman.delivery.domain.entity.Shipper;
import com.nangman.delivery.domain.enums.ShipperType;
import com.nangman.delivery.domain.repository.ShipperRepository;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final ZSetOperations<String, String> zSetOperations;
    private final ShipperRepository shipperRepository;
    private final RedissonClient redissonClient;

    public void addShipperZSet(Shipper shipper, double score) {
        zSetOperations.add(generateKey(shipper.getShipperType(), shipper.getHubId()), shipper.getId().toString(), score);
    }

    public UUID getShipperZSet(ShipperType shipperType, UUID hubId, long distance) {
        String key = generateKey(shipperType, hubId);
        String lockKey = "lock:" + key;

        RLock lock = redissonClient.getLock(lockKey);
        boolean isLocked = false;

        try {
            isLocked = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (isLocked) {
                Set<String> shippers = zSetOperations.rangeByScore(key, Long.MIN_VALUE, Long.MAX_VALUE, 0, 1);

                if (shippers == null || shippers.isEmpty()) {
                    List<Shipper> shipperList = shipperRepository.findByHubIdAndShipperType(hubId, shipperType);
                    if (shipperList.isEmpty())
                        throw new InfraStructureException(ExceptionStatus.SHIPPER_NOT_FOUND);
                    shipperList.forEach(shipper -> addShipperZSet(shipper, shipper.getCurrentDistance()));
                    shippers = zSetOperations.rangeByScore(key, Long.MIN_VALUE, Long.MAX_VALUE, 0, 1);
                }
                if(shippers == null || shippers.isEmpty())
                    throw new InfraStructureException(ExceptionStatus.SHIPPER_NOT_FOUND);
                String shipperIdString = Objects.requireNonNull(shippers).iterator().next();
                UUID shipperId = UUID.fromString(shipperIdString);
                zSetOperations.incrementScore(key, shipperIdString, distance);
                return shipperId;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (isLocked)
                lock.unlock();
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
