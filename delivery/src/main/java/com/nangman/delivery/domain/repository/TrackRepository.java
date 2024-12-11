package com.nangman.delivery.domain.repository;

import com.nangman.delivery.domain.entity.Track;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, UUID> {
}
