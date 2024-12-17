package com.nangman.delivery.domain.repository;

import com.nangman.delivery.common.exception.DomainException;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.domain.entity.Delivery;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID>, QuerydslPredicateExecutor<Delivery> {

    @NonNull
    default Delivery getById(@NonNull UUID deliveryId) {
        return getByIdAndIsDeleteIsFalse(deliveryId).orElseThrow(
                () -> new DomainException(ExceptionStatus.DELIVERY_NOT_FOUND));
    }

    Optional<Delivery> getByIdAndIsDeleteIsFalse(UUID deliveryId);
}
