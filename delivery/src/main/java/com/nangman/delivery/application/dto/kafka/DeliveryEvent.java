package com.nangman.delivery.application.dto.kafka;

import com.nangman.delivery.application.dto.request.DeliveryPostRequest;
import com.nangman.delivery.application.dto.response.DeliveryResponse;
import java.util.UUID;

public record DeliveryEvent (UUID deliveryId, DeliveryResponse message){}
