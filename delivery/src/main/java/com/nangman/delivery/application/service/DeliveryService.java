package com.nangman.delivery.application.service;

import com.nangman.delivery.application.dto.request.DeliveryPostRequest;
import com.nangman.delivery.application.dto.response.DeliveryResponse;
import com.nangman.delivery.common.exception.ApplicationException;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.domain.entity.Delivery;
import com.nangman.delivery.domain.repository.DeliveryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public DeliveryResponse createDelivery(DeliveryPostRequest request) {
        return DeliveryResponse.from(deliveryRepository.save(request.toEntity()));
    }

    public DeliveryResponse getDeliveryByDeliveryId(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
            .orElseThrow(() -> new ApplicationException(ExceptionStatus.DELIVERY_NOT_FOUND));
        return DeliveryResponse.from(delivery);
    }
}
