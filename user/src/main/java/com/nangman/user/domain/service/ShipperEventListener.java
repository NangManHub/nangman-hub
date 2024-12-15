package com.nangman.user.domain.service;

import com.nangman.user.application.dto.kafka.ShipperEvent;
import com.nangman.user.infrastructure.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShipperEventListener {

    private final KafkaProducerService kafkaProducerService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleShipperEvent(ShipperEvent event) {
        kafkaProducerService.sendShipperMessage(event.shipperId(), event.message());
    }
}
