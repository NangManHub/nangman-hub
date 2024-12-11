package com.nangman.delivery.application.service;

import com.nangman.delivery.application.dto.request.DeliveryPostRequest;
import com.nangman.delivery.application.dto.request.DeliveryPutRequest;
import com.nangman.delivery.application.dto.response.DeliveryResponse;
import com.nangman.delivery.common.exception.ApplicationException;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.domain.entity.Delivery;
import com.nangman.delivery.domain.repository.DeliveryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public DeliveryResponse createDelivery(DeliveryPostRequest request) {
        return DeliveryResponse.from(deliveryRepository.save(request.toEntity()));
    }

    @Transactional(readOnly = true)
    public DeliveryResponse getDeliveryById(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ApplicationException(ExceptionStatus.DELIVERY_NOT_FOUND));
        return DeliveryResponse.from(delivery);
    }

    @Transactional
    public DeliveryResponse updateDelivery(UUID deliveryId, DeliveryPutRequest request) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ApplicationException(ExceptionStatus.DELIVERY_NOT_FOUND));

        delivery.update(request.status(), request.fromHubId(), request.toHubId(), request.address(),
                request.recipient(), request.orderId());

        return DeliveryResponse.from(delivery);
    }

    @Transactional
    public void deleteDeliveryById(UUID deliveryId, UUID userId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ApplicationException(ExceptionStatus.DELIVERY_NOT_FOUND));

        delivery.delete(userId);
    }
}
