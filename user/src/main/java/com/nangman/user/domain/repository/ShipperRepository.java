package com.nangman.user.domain.repository;

import com.nangman.user.common.exception.CustomException;
import com.nangman.user.common.exception.ExceptionType;
import com.nangman.user.domain.entity.Shipper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShipperRepository {

    Shipper save(Shipper shipper);

    Optional<Shipper> findByIdAndIsDeletedFalse(UUID shipperId);

    default Shipper findShipper(UUID shipperId){
        return findByIdAndIsDeletedFalse(shipperId).orElseThrow(() -> new CustomException(ExceptionType.SHIPPER_NOT_FOUND));
    }
}
