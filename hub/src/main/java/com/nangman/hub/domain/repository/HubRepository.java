package com.nangman.hub.domain.repository;

import com.nangman.hub.common.exception.CustomException;
import com.nangman.hub.common.exception.ExceptionCode;
import com.nangman.hub.domain.entity.Hub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRepository extends JpaRepository<Hub, UUID>, QuerydslPredicateExecutor<Hub> {
    Optional<Hub> findByIdAndIsDeleteFalse(UUID hubId);

    List<Hub> findAllByParentHubIdIsNullAndIdNotAndIsDeleteFalse(UUID hubId);

    default Hub findHub(UUID hubId) {
        return findByIdAndIsDeleteFalse(hubId)
                .orElseThrow(() -> new CustomException(ExceptionCode.HUB_NOT_FOUND));
    }
}
