package com.nangman.delivery.infrastructure;

import com.nangman.delivery.application.dto.response.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<UUID, DeliveryResponse> kafkaDeliveryResponseTemplate;

    public void sendDeliverySuccessMessage(UUID uuid, DeliveryResponse message) {
        kafkaDeliveryResponseTemplate.send("delivery.create-success", uuid, message);
    }
}
