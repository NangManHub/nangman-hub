package com.nangman.delivery.domain.repository;

import com.nangman.delivery.domain.entity.Delivery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
