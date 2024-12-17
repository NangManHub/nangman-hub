package com.nangman.ai.domain.service;

import com.nangman.ai.application.dto.kafka.AIEvent;
import com.nangman.ai.infrastructure.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class AIEventListener {

    private final KafkaProducer kafkaProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAIEvent(AIEvent event) {
        kafkaProducer.sendAIMessage(event.aiId(), event.message());
    }
}
