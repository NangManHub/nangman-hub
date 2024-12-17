package com.nangman.ai.infrastructure;

import com.nangman.ai.application.dto.kafka.DeliveryResponse;
import com.nangman.ai.application.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final AIService aiService;

    @KafkaListener(topics = "delivery.create-success", groupId = "ai")
    public void consumeDeliverySuccess(DeliveryResponse message) {
        aiService.createMessage(message.toAIPostRequest());
    }
}
