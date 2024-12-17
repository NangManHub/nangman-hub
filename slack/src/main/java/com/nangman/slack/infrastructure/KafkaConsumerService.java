package com.nangman.slack.infrastructure;

import com.nangman.slack.application.dto.kafka.AIMessage;
import com.nangman.slack.application.dto.kafka.DeliveryResponse;
import com.nangman.slack.application.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final MessageService messageService;

    @KafkaListener(topics = "delivery.create-success", groupId = "slack-delivery-id", containerFactory = "deliveryKafkaListenerContainerFactory")
    public void consumeDeliveryMessage(DeliveryResponse deliveryInfo){
        messageService.sendDeliveryInfoToShipper(deliveryInfo);
    }

    @KafkaListener(topics = "ai.message-success", groupId = "slack-ai-id", containerFactory = "aiKafkaListenerContainerFactory")
    public void consumeAiMessage(AIMessage aiMessage){
        messageService.sendOrderInfoToManger(aiMessage);
    }
}
