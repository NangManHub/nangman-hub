package com.nangman.ai.domain.repository;

import com.nangman.ai.domain.entity.AI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AIRepository extends JpaRepository<AI, UUID> {
}
