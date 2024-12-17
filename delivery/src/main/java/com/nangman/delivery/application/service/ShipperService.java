package com.nangman.delivery.application.service;

import com.nangman.delivery.application.dto.kafka.ShipperMessage;
import com.nangman.delivery.domain.entity.Shipper;
import com.nangman.delivery.domain.repository.ShipperRepository;
import com.nangman.delivery.infrastructure.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final ShipperRepository shipperRepository;
    private final RedisService redisService;

    @Transactional
    public void addShipper(ShipperMessage shipperMessage) {
        Shipper shipper = shipperRepository.save(shipperMessage.toEntity());

        redisService.addShipperZSet(shipper, 0);
    }

    @Transactional
    public void updateShipper(ShipperMessage shipperMessage) {
        Shipper shipper = shipperRepository.getById(shipperMessage.userId());

        shipper.update(shipperMessage.hubId(), shipperMessage.type());
    }

    @Transactional
    public void deleteShipper(ShipperMessage shipperMessage) {
        Shipper shipper = shipperRepository.getById(shipperMessage.userId());

        redisService.deleteShipperZSet(shipper);
        shipperRepository.delete(shipper);
    }
}
