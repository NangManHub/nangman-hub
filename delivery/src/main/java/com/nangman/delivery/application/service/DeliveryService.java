package com.nangman.delivery.application.service;

import com.nangman.delivery.application.dto.request.DeliveryPostRequest;
import com.nangman.delivery.application.dto.request.DeliveryPutRequest;
import com.nangman.delivery.application.dto.request.DeliverySearchRequest;
import com.nangman.delivery.application.dto.response.DeliveryResponse;
import com.nangman.delivery.domain.entity.Delivery;
import com.nangman.delivery.domain.entity.QDelivery;
import com.nangman.delivery.domain.repository.DeliveryRepository;
import com.querydsl.core.BooleanBuilder;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Delivery delivery = deliveryRepository.getById(deliveryId);

        return DeliveryResponse.from(delivery);
    }

    @Transactional(readOnly = true)
    public Page<DeliveryResponse> searchDelivery(DeliverySearchRequest searchRequest, Pageable pageable) {

        BooleanBuilder qBuilder = new BooleanBuilder();
        qBuilder.and(QDelivery.delivery.isDelete.isFalse());
        if (searchRequest.status() != null) {
            qBuilder.and(QDelivery.delivery.status.eq(searchRequest.status()));
        }
        if (searchRequest.fromHubId() != null) {
            qBuilder.and(QDelivery.delivery.fromHubId.eq(searchRequest.fromHubId()));
        }
        if (searchRequest.toHubId() != null) {
            qBuilder.and(QDelivery.delivery.toHubId.eq(searchRequest.toHubId()));
        }
        if (searchRequest.address() != null) {
            qBuilder.and(QDelivery.delivery.address.contains(searchRequest.address()));
        }
        if (searchRequest.recipient() != null) {
            qBuilder.and(QDelivery.delivery.recipient.eq(searchRequest.recipient()));
        }
        if (searchRequest.orderId() != null) {
            qBuilder.and(QDelivery.delivery.orderId.eq(searchRequest.orderId()));
        }
        return deliveryRepository.findAll(qBuilder, pageable).map(DeliveryResponse::from);
    }

    @Transactional
    public DeliveryResponse updateDelivery(UUID deliveryId, DeliveryPutRequest request) {
        Delivery delivery = deliveryRepository.getById(deliveryId);

        delivery.update(request.status(), request.fromHubId(), request.toHubId(), request.address(),
                request.recipient(), request.orderId());

        return DeliveryResponse.from(delivery);
    }

    @Transactional
    public void deleteDeliveryById(UUID deliveryId, UUID userId) {
        Delivery delivery = deliveryRepository.getById(deliveryId);

        delivery.delete(userId);
    }

}
