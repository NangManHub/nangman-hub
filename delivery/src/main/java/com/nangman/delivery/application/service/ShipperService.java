package com.nangman.delivery.application.service;

import com.nangman.delivery.application.dto.kafka.ShipperMessage;
import com.nangman.delivery.domain.repository.ShipperRepository;
import com.nangman.delivery.domain.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final ShipperRepository shipperRepository;
    private final RedisService redisService;

    public void upsertShipper(ShipperMessage shipperMessage) {
        shipperRepository.save(shipperMessage.toEntity());
    }

    public void deleteShipper(ShipperMessage shipperMessage) {
        shipperRepository.deleteById(shipperMessage.userId());
    }
}
