package com.nangman.hub.domain.repository;

import com.nangman.hub.common.exception.CustomException;
import com.nangman.hub.common.exception.ExceptionCode;
import com.nangman.hub.domain.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;
import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID>, QuerydslPredicateExecutor<Route> {
    Optional<Route> findByIdAndIsDeleteFalse(UUID routeId);

    default Route findRoute(UUID routeId) {
        return findByIdAndIsDeleteFalse(routeId)
                .orElseThrow(() -> new CustomException(ExceptionCode.ROUTE_NOT_FOUND));
    }
}
