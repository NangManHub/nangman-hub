package com.nangman.hub.domain.repository;

import com.nangman.hub.domain.entity.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HubRepository extends JpaRepository<Hub, UUID> {
    Optional<Hub> findByIdAndIsDeleteFalse(UUID hubId);
}
