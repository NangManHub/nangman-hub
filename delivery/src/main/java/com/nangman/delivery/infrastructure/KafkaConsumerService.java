package com.nangman.delivery.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nangman.delivery.application.dto.kafka.ActionType;
import com.nangman.delivery.application.dto.kafka.OrderEvent;
import com.nangman.delivery.application.dto.kafka.ShipperMessage;
import com.nangman.delivery.application.dto.request.DeliveryPostRequest;
import com.nangman.delivery.application.service.DeliveryService;
import com.nangman.delivery.application.service.ShipperService;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.common.exception.InfraStructureException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final ShipperService shipperService;
    private final DeliveryService deliveryService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user.shipper.events", groupId = "delivery", containerFactory = "kafkaShipperMessageListenerContainerFactory")
    public void consumeShipperEvent(ShipperMessage message) {

        try {
            switch (ActionType.valueOf(message.action())) {
                case CREATE:
                    shipperService.addShipper(message);
                    break;
                case UPDATE:
                    shipperService.updateShipper(message);
                    break;
                case DELETE:
                    shipperService.deleteShipper(message);
                    break;
                default:
            }
        } catch (IllegalArgumentException e) {
            throw new InfraStructureException(ExceptionStatus.SHIPPER_MESSAGE_INVALID_ACTION);
        }
    }

    @KafkaListener(topics = "order.create-success", groupId = "delivery", containerFactory = "kafkaStringListenerContainerFactory")
    public void consumeOrderCreateSuccessEvent(String message) {
        try {
            OrderEvent orderEvent = objectMapper.readValue(message, OrderEvent.class);
            DeliveryPostRequest deliveryPostRequest = DeliveryPostRequest.builder()
                    .orderId(orderEvent.orderId())
                    .recipient(orderEvent.agentId())
                    .toHubId(orderEvent.toHubId())
                    .fromHubId(orderEvent.fromHubId())
                    .address(orderEvent.address())
                    .build();

            deliveryService.createDelivery(deliveryPostRequest);
        } catch (JsonProcessingException e) {
            throw new InfraStructureException(ExceptionStatus.ORDER_MESSAGE_INVALID);
        }
    }
}
