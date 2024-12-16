package com.nangman.delivery.application.service;

import com.nangman.delivery.application.dto.request.DeliveryPostRequest;
import com.nangman.delivery.application.dto.request.DeliveryPutRequest;
import com.nangman.delivery.application.dto.request.DeliverySearchRequest;
import com.nangman.delivery.application.dto.response.DeliveryResponse;
import com.nangman.delivery.common.util.AuthorizationUtils;
import com.nangman.delivery.domain.entity.Delivery;
import com.nangman.delivery.domain.entity.QDelivery;
import com.nangman.delivery.domain.entity.Track;
import com.nangman.delivery.domain.enums.UserRole;
import com.nangman.delivery.domain.repository.DeliveryRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final AuthorizationUtils authorizationUtils;
    private final TrackService trackService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public DeliveryResponse createDelivery(DeliveryPostRequest request) {
        Delivery delivery = request.toEntity();

        List<Track> tracks = trackService.createTrack(request.fromHubId(), request.toHubId());
        delivery.addTracks(tracks);

        Track companyTrack = trackService.createCompanyTrack(request.toHubId(), request.address());
        delivery.addTrack(companyTrack);

        DeliveryResponse response = DeliveryResponse.from(deliveryRepository.save(delivery));
        eventPublisher.publishEvent(response);

        return response;
    }

    @Transactional(readOnly = true)
    public DeliveryResponse getDeliveryById(UUID deliveryId) {
        authorizationUtils.validateDeliveryHubManager(deliveryId);
        authorizationUtils.validateDeliveryShipper(deliveryId);

        Delivery delivery = deliveryRepository.getById(deliveryId);

        return DeliveryResponse.from(delivery);
    }

    @Transactional(readOnly = true)
    public Page<DeliveryResponse> searchDelivery(DeliverySearchRequest searchRequest, Pageable pageable) {
        Predicate managerAndShipperExpression = getManagerAndShipperPredicate();

        QDelivery delivery = QDelivery.delivery;

        Predicate predicate = ExpressionUtils.allOf(
                delivery.isDelete.isFalse(),
                managerAndShipperExpression,
                searchRequest.status() != null ? delivery.status.eq(searchRequest.status()) : null,
                searchRequest.fromHubId() != null ? delivery.fromHubId.eq(searchRequest.fromHubId()) : null,
                searchRequest.toHubId() != null ? delivery.toHubId.eq(searchRequest.toHubId()) : null,
                searchRequest.address() != null ? delivery.address.contains(searchRequest.address()) : null,
                searchRequest.recipient() != null ? delivery.recipient.eq(searchRequest.recipient()) : null,
                searchRequest.orderId() != null ? delivery.orderId.eq(searchRequest.orderId()) : null
        );

        return deliveryRepository.findAll(Objects.requireNonNull(predicate), pageable).map(DeliveryResponse::from);
    }

    @Transactional
    public DeliveryResponse updateDelivery(UUID deliveryId, DeliveryPutRequest request) {
        authorizationUtils.validateDeliveryHubManager(deliveryId);
        authorizationUtils.validateDeliveryShipper(deliveryId);
        Delivery delivery = deliveryRepository.getById(deliveryId);

        delivery.update(request.status(), request.fromHubId(), request.toHubId(), request.address(),
                request.recipient(), request.orderId());

        return DeliveryResponse.from(delivery);
    }

    @Transactional
    public void deleteDeliveryById(UUID deliveryId, UUID userId) {
        authorizationUtils.validateDeliveryHubManager(deliveryId);
        Delivery delivery = deliveryRepository.getById(deliveryId);

        delivery.delete(userId);
    }

    private Predicate getManagerAndShipperPredicate() {
        UserRole userRole = authorizationUtils.getUserRoleFromHeader();
        UUID userId = authorizationUtils.getUserIdFromHeader();

//        if (userRole == UserRole.MANAGER) {
//            UUID hubId = hubClient.getHubIdByManagerId(userId);
//            return QDelivery.delivery.toHubId.eq(hubId).or(QDelivery.delivery.fromHubId.eq(hubId));
//        } else if (userRole == UserRole.SHIPPER) {
//            return QDelivery.delivery.track.shipperId.eq(userId);
//        }
        return null;
    }
}
