package com.nangman.delivery.domain.repository;

import com.nangman.delivery.common.exception.DomainException;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.domain.entity.Shipper;
import com.nangman.delivery.domain.enums.ShipperType;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipperRepository extends JpaRepository<Shipper, UUID> {
    @NonNull
    default Shipper getById(@NonNull UUID userId) {
        return findById(userId).orElseThrow(() -> new DomainException(ExceptionStatus.SHIPPER_NOT_FOUND));
    }

    List<Shipper> findByHubIdAndShipperType(UUID hubId, ShipperType shipperType);
}
