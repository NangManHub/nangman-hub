package com.nangman.delivery.domain.service;

import com.nangman.delivery.application.dto.kafka.DeliveryEvent;
import com.nangman.delivery.infrastructure.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class DeliveryEventListener {
    private final KafkaProducerService kafkaProducerService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDeliveryEvent(DeliveryEvent event) {
        kafkaProducerService.sendDeliveryMessage(event.deliveryId(), event.message());
    }
}