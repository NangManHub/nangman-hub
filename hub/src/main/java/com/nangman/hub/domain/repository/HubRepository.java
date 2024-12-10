package com.nangman.hub.domain.repository;

import com.nangman.hub.domain.entity.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, Long> {
}
