package com.nangman.delivery.application.service;

import com.nangman.delivery.application.dto.request.DeliveryPostRequest;
import com.nangman.delivery.application.dto.response.DeliveryResponse;
import com.nangman.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public DeliveryResponse createDelivery(DeliveryPostRequest request) {
        return DeliveryResponse.from(deliveryRepository.save(request.toEntity()));
    }
}
