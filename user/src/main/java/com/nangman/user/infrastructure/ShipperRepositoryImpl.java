package com.nangman.user.infrastructure;

import com.nangman.user.domain.entity.Shipper;
import com.nangman.user.domain.repository.ShipperRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShipperRepositoryImpl  extends JpaRepository<Shipper, UUID>, ShipperRepository {
}
