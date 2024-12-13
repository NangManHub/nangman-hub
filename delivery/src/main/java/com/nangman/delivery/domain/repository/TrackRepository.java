package com.nangman.delivery.domain.repository;

import com.nangman.delivery.common.exception.DomainException;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.domain.entity.Track;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TrackRepository extends JpaRepository<Track, UUID>, QuerydslPredicateExecutor<Track> {

    @NonNull
    default Track getById(@NonNull UUID trackId) {
        return getByIdAndIsDeleteIsFalse(trackId).orElseThrow(
                () -> new DomainException(ExceptionStatus.TRACK_NOT_FOUND));
    }

    Optional<Track> getByIdAndIsDeleteIsFalse(UUID trackId);
}
