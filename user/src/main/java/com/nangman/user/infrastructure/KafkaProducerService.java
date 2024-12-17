package com.nangman.user.infrastructure;

import com.nangman.user.application.dto.kafka.ShipperMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<UUID, ShipperMessage> kafkaTemplate;

    public void sendShipperMessage(UUID key, ShipperMessage message) {
        kafkaTemplate.send("user.shipper.events", key, message);
    }
}
