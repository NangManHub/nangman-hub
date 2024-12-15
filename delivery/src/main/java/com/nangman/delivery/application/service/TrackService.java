package com.nangman.delivery.application.service;

import com.nangman.delivery.application.dto.feign.RouteDetailResponse;
import com.nangman.delivery.application.dto.request.TrackCompletionPatchRequest;
import com.nangman.delivery.application.dto.request.TrackPutRequest;
import com.nangman.delivery.application.dto.request.TrackSearchRequest;
import com.nangman.delivery.application.dto.response.TrackResponse;
import com.nangman.delivery.common.util.AuthorizationUtils;
import com.nangman.delivery.domain.entity.QTrack;
import com.nangman.delivery.domain.entity.Shipper;
import com.nangman.delivery.domain.entity.Track;
import com.nangman.delivery.domain.enums.ShipperType;
import com.nangman.delivery.domain.enums.UserRole;
import com.nangman.delivery.domain.repository.ShipperRepository;
import com.nangman.delivery.domain.repository.TrackRepository;
import com.nangman.delivery.infrastructure.HubClient;
import com.nangman.delivery.infrastructure.RedisService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;
    private final ShipperRepository shipperRepository;
    private final AuthorizationUtils authorizationUtils;
    private final RedisService redisService;
    private final HubClient hubClient;

    @Transactional
    public TrackResponse updateTrack(UUID trackId, TrackPutRequest request) {
        authorizationUtils.validateTrackHubManager(trackId);
        authorizationUtils.validateTrackShipper(trackId);
        Track track = trackRepository.getById(trackId);
        Shipper shipper = shipperRepository.getById(request.shipperId());

        track.update(request.sequence(), shipper, request.fromHubId(), request.toHubId(), request.address(),
                request.expectDistance(), request.expectTime(), request.actualDistance(), request.actualTime(),
                request.status(), request.departureTime());

        return TrackResponse.from(track);
    }

    @Transactional
    public TrackResponse completeTrack(UUID trackId, TrackCompletionPatchRequest request) {
        authorizationUtils.validateTrackHubManager(trackId);
        authorizationUtils.validateTrackShipper(trackId);

        Track track = trackRepository.getById(trackId);

        track.complete(request.actualDistance());
        redisService.completeTask(track.getShipper(), track.getExpectDistance());

        return TrackResponse.from(track);
    }

    @Transactional
    public TrackResponse departureTrack(UUID trackId) {
        authorizationUtils.validateTrackHubManager(trackId);
        authorizationUtils.validateTrackShipper(trackId);

        Track track = trackRepository.getById(trackId);

        track.departureTrack();

        return TrackResponse.from(track);
    }

    public Page<TrackResponse> searchTrack(TrackSearchRequest searchRequest, Pageable pageable) {
        Predicate managerAndShipperExpression = getManagerAndShipperPredicate();

        QTrack track = QTrack.track;

        Predicate predicate = ExpressionUtils.allOf(
                track.isDelete.isFalse(),
                managerAndShipperExpression,
                searchRequest.deliveryId() != null ? track.delivery.id.eq(searchRequest.deliveryId()) : null,
                searchRequest.sequence() != null ? track.sequence.eq(searchRequest.sequence()) : null,
                searchRequest.shipperId() != null ? track.shipper.id.eq(searchRequest.shipperId()) : null,
                searchRequest.fromHubId() != null ? track.fromHubId.eq(searchRequest.fromHubId()) : null,
                searchRequest.toHubId() != null ? track.toHubId.eq(searchRequest.toHubId()) : null,
                searchRequest.address() != null ? track.address.contains(searchRequest.address()) : null,
                searchRequest.expectDistance() != null ? track.expectDistance.eq(searchRequest.expectDistance()) : null,
                searchRequest.expectTime() != null ? track.expectTime.eq(searchRequest.expectTime()) : null,
                searchRequest.actualDistance() != null ? track.actualDistance.eq(searchRequest.actualDistance()) : null,
                searchRequest.actualTime() != null ? track.actualTime.eq(searchRequest.actualTime()) : null,
                searchRequest.status() != null ? track.status.eq(searchRequest.status()) : null,
                searchRequest.departureTime() != null ? track.departureTime.eq(searchRequest.departureTime()) : null
        );

        return trackRepository.findAll(Objects.requireNonNull(predicate), pageable).map(TrackResponse::from);
    }


    private Predicate getManagerAndShipperPredicate() {
        UserRole userRole = authorizationUtils.getUserRoleFromHeader();
        UUID userId = authorizationUtils.getUserIdFromHeader();

//        if (userRole == UserRole.MANAGER) {
//            UUID hubId = hubClient.getHubIdByManagerId(userId);
//            return Qtrack.track.toHubId.eq(hubId).or(Qtrack.track.fromHubId.eq(hubId));
//        } else if (userRole == UserRole.SHIPPER) {
//            return Qtrack.track.shipperId.eq(userId);
//        }
        return null;
    }

    public List<Track> createTrack(@NotNull UUID fromHubId, @NotNull UUID toHubId) {
        List<RouteDetailResponse> routeDetailResponses = hubClient.getRouts(fromHubId, toHubId);
        AtomicReference<Integer> sequence = new AtomicReference<>(1);

        return routeDetailResponses.stream()
                .map(routeDetailResponse -> {
                    boolean isCenter = routeDetailResponse.toHub().parentHubId() == null;
                    UUID shipperId = redisService.getShipperZSet(ShipperType.HUB, isCenter ? routeDetailResponse.toHub().hubId() : routeDetailResponse.toHub().parentHubId(), routeDetailResponse.distance());
                    Shipper shipper = shipperRepository.getById(shipperId);
                    return Track.builder()
                            .sequence(sequence.getAndSet(sequence.get() + 1))
                            .shipper(shipper)
                            .fromHubId(fromHubId)
                            .toHubId(toHubId)
                            .expectDistance(routeDetailResponse.distance())
                            .expectTime(routeDetailResponse.duration())
                            .routeId(routeDetailResponse.routeId())
                            .build();
                })
                .toList();
        //TODO: Company 배송 생성
    }

    public Track createCompanyTrack(@NotNull UUID hubId, @NotNull String address) {
        Shipper shipper = shipperRepository.getById(redisService.getShipperZSet(ShipperType.COMPANY, hubId, 0));
        return Track.builder()
                .sequence(1)
                .shipper(shipper)
                .fromHubId(hubId)
                .address(address)
                .expectDistance(0)
                .expectTime(0)
                .build();
    }
}
