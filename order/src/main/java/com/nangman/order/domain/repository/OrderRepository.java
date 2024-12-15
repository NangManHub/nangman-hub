package com.nangman.order.domain.repository;

import com.nangman.order.common.exception.OrderNotFoundException;
import com.nangman.order.domain.entity.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findByIdAndIsDeleteFalse(UUID id);

    default Order getById(UUID orderId) {
        return findByIdAndIsDeleteFalse(orderId).orElseThrow(OrderNotFoundException::new);
    }
}
