package com.nangman.delivery.infrastructure;

import com.nangman.delivery.application.dto.kafka.ShipperMessage;
import com.nangman.delivery.application.service.ShipperService;
import com.nangman.delivery.domain.repository.ShipperRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final ShipperService shipperService;

    @KafkaListener(topics = "user.shipper.events")
    public void consumeShipperEvent(UUID key, ShipperMessage message) {
        System.out.println("Consumed message: " + message);
        if(message.action() == "delete")
            shipperService.deleteShipper(message);
        else
            shipperService.upsertShipper(message);
    }
}
