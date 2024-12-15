package com.nangman.delivery.infrastructure;

import com.nangman.delivery.application.dto.kafka.ShipperMessage;
import com.nangman.delivery.application.service.ShipperService;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.common.exception.InfraStructureException;
import com.nangman.delivery.domain.repository.ShipperRepository;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final ShipperService shipperService;

    @KafkaListener(topics = "user.shipper.events", groupId = "delivery-shipper-id")
    public void consumeShipperEvent(UUID key, ShipperMessage message) {
        System.out.println("Consumed message: " + message);

        switch (message.action()) {
            case "create":
                shipperService.addShipper(message);
                break;
            case "update":
                shipperService.updateShipper(message);
                break;
            case "delete":
                shipperService.deleteShipper(message);
                break;
            default:
                throw new InfraStructureException(ExceptionStatus.SHIPPER_MESSAGE_INVALID_ACTION);
        }
    }
}
