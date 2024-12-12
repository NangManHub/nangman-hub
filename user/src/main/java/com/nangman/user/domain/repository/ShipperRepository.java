package com.nangman.user.domain.repository;

import com.nangman.user.domain.entity.Shipper;
import com.nangman.user.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository {

    Shipper save(Shipper shipper);
}
