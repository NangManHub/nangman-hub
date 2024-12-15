package com.nangman.delivery.application.service;

import com.nangman.delivery.application.dto.kafka.ShipperMessage;
import com.nangman.delivery.domain.entity.Shipper;
import com.nangman.delivery.domain.repository.ShipperRepository;
import com.nangman.delivery.infrastructure.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final ShipperRepository shipperRepository;
    private final RedisService redisService;

    public void addShipper(ShipperMessage shipperMessage) {
        Shipper shipper = shipperRepository.save(shipperMessage.toEntity());

        redisService.addShipperZSet(shipper, 0);
    }

    public void updateShipper(ShipperMessage shipperMessage) {
        Shipper shipper = shipperRepository.getById(shipperMessage.userId());

        shipper.update(shipperMessage.hubId(), shipperMessage.type());
    }

    public void deleteShipper(ShipperMessage shipperMessage) {
        Shipper shipper = shipperRepository.getById(shipperMessage.userId());

        redisService.deleteShipperZSet(shipper);
        shipper.delete();
    }
}
