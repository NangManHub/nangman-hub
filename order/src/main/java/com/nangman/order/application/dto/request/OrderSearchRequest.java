package com.nangman.order.application.dto.request;

import jakarta.validation.constraints.Min;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public record OrderSearchRequest (
    UUID supplierId,
    UUID receiverId,
    UUID productId,
    @Min(0) Integer productQuantity,
    String requestMessage) {
}