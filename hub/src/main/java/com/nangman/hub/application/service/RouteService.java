package com.nangman.hub.application.service;

import com.nangman.hub.application.dto.request.NaverRouteRequest;
import com.nangman.hub.application.dto.request.RoutePostRequest;
import com.nangman.hub.application.dto.request.RouteSearchRequest;
import com.nangman.hub.application.dto.response.NaverRouteResponse;
import com.nangman.hub.application.dto.response.RouteDetailResponse;
import com.nangman.hub.application.dto.response.RouteResponse;
import com.nangman.hub.domain.entity.Hub;
import com.nangman.hub.domain.entity.QRoute;
import com.nangman.hub.domain.entity.Route;
import com.nangman.hub.domain.repository.HubRepository;
import com.nangman.hub.domain.repository.RouteRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class RouteService {

    private final NaverService naverService;
    private final RouteRepository routeRepository;
    private final HubRepository hubRepository;

    public RouteResponse createRoute(RoutePostRequest postRequest) {
        Hub fromHub = hubRepository.findHub(postRequest.fromHubId());
        Hub toHub = hubRepository.findHub(postRequest.toHubId());
        Route route = routeRepository.save(postRequest.toEntity(fromHub, toHub));
        return RouteResponse.from(route);
    }

    public void createRouteWithHub(Hub fromHub, Hub toHub) {
        List<NaverRouteResponse> directions = naverService.getBiDirections(
                new NaverRouteRequest(fromHub.getLatitude(), fromHub.getLongitude()),
                new NaverRouteRequest(toHub.getLatitude(), toHub.getLongitude())
        );
        routeRepository.saveAll(List.of(
                new Route(fromHub, toHub, directions.get(0).duration(), directions.get(0).distance()),
                new Route(toHub, fromHub, directions.get(1).duration(), directions.get(1).distance())
        ));
    }

    @Transactional(readOnly = true)
    public Page<RouteResponse> getRoutes(RouteSearchRequest searchRequest, Pageable pageable) {
        BooleanBuilder qBuilder = new BooleanBuilder();
        qBuilder.and(QRoute.route.isDelete.isFalse());
        if (searchRequest.fromHubId() != null) {
            qBuilder.and(QRoute.route.fromHub.id.eq(searchRequest.fromHubId()));
        }
        if (searchRequest.toHubId() != null) {
            qBuilder.and(QRoute.route.toHub.id.eq(searchRequest.toHubId()));
        }
        if (searchRequest.distance() != null) {
            qBuilder.and(QRoute.route.distance.goe(searchRequest.distance()));
        }
        if (searchRequest.duration() != null) {
            qBuilder.and(QRoute.route.duration.goe(searchRequest.duration()));
        }
        return routeRepository.findAll(qBuilder, pageable).map(RouteResponse::from);
    }

    @Transactional(readOnly = true)
    public RouteResponse getRouteById(UUID routeId) {
        return RouteResponse.from(routeRepository.findRoute(routeId));
    }

    @Transactional(readOnly = true)
    public List<RouteDetailResponse> getBestRoutes(UUID fromHubId, UUID toHubId) {
        List<RouteDetailResponse> res = new ArrayList<>();

        Hub fromHub = hubRepository.findHub(fromHubId);
        Hub nextHub = fromHub.getParentHub();
        if (nextHub != null) {
            res.add(RouteDetailResponse.from(routeRepository.findRouteByHub(fromHub.getId(), nextHub.getId())));
        } else {
            nextHub = fromHub;
        }

        Hub toHub = hubRepository.findHub(toHubId);
        Hub prevHub = toHub.getParentHub();
        if (prevHub != null) {
            res.add(RouteDetailResponse.from(routeRepository.findRouteByHub(nextHub.getId(), prevHub.getId())));
            nextHub = prevHub;
        }

        res.add(RouteDetailResponse.from(routeRepository.findRouteByHub(nextHub.getId(), toHub.getId())));

        return res;
    }

    public RouteResponse updateRoute(UUID routeId, RoutePostRequest postRequest) {
        Route route = routeRepository.findRoute(routeId);

        Hub curFromHub = route.getFromHub();
        Hub curToHub = route.getToHub();
        Hub newFromHub = curFromHub.getId() != postRequest.fromHubId() ? hubRepository.findHub(postRequest.fromHubId()) : curFromHub;
        Hub newToHub = curToHub.getId() != postRequest.toHubId() ? hubRepository.findHub(postRequest.toHubId()) : curToHub;
        route.update(
                newFromHub,
                newToHub,
                postRequest.distance(),
                postRequest.duration()
        );
        return RouteResponse.from(route);
    }

    public void deleteRoute(UUID routeId, UUID userId) {
        Route route = routeRepository.findRoute(routeId);
        route.delete(userId);
    }
}
