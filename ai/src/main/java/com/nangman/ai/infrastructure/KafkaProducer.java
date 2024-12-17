package com.nangman.ai.infrastructure;

import com.nangman.ai.application.dto.kafka.AIMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<UUID, AIMessage> kafkaTemplate;

    public void sendAIMessage(UUID key, AIMessage message) {
        kafkaTemplate.send("ai.message-success", key, message);
    }
}
