package com.nangman.delivery.infrastructure;

import com.nangman.delivery.application.dto.kafka.ActionType;
import com.nangman.delivery.application.dto.kafka.ShipperMessage;
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

    @KafkaListener(topics = "user.shipper.events", groupId = "delivery-shipper-id")
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
}
