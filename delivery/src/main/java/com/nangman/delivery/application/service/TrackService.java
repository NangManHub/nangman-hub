package com.nangman.delivery.application.service;

import com.nangman.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final DeliveryRepository deliveryRepository;
}
