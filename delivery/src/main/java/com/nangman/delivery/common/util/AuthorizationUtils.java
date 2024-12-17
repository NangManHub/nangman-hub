package com.nangman.delivery.common.util;

import com.nangman.delivery.common.exception.AuthException;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.domain.entity.Delivery;
import com.nangman.delivery.domain.entity.Track;
import com.nangman.delivery.domain.enums.UserRole;
import com.nangman.delivery.domain.repository.DeliveryRepository;
import com.nangman.delivery.domain.repository.TrackRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationUtils {

    private final DeliveryRepository deliveryRepository;
    private final TrackRepository trackRepository;
    private final HttpServletRequest request;

    public void validateHubManager(UUID requestHubId) {
        if (getUserRoleFromHeader() != UserRole.MANAGER)
            return;
//         UUID hubManagerId = hubClient.getHubByManagerId(managerId);
//        if (!requestHubId.equals(hubManagerId)) {
//            throw new AuthException(ExceptionStatus.AUTHORIZATION_FAILED);
//        }
    }


    public void validateTrackShipper(UUID trackId) {
        if (getUserRoleFromHeader() == UserRole.SHIPPER)
            return;
        UUID shipperId = getUserIdFromHeader();
        Track track = trackRepository.getById(trackId);
        UUID hubManagerId = UUID.randomUUID();
        if (!track.getShipper().getId().equals(shipperId)) {
            throw new AuthException(ExceptionStatus.AUTHORIZATION_FAILED);
        }
    }


    public void validateTrackHubManager(UUID trackId) {
        if (getUserRoleFromHeader() == UserRole.MANAGER)
            return;
        UUID managerId = getUserIdFromHeader();
        Track track = trackRepository.getById(trackId);

        //TODO: HubManager 정보 받아오기
        //UUID toHubManagerId = hubClient.getMangerIdByHubId(track.getFromHubId());
        //UUID fromHManagerId = hubClient.getMangerIdByHubId(track.getFromHubId());

//        if (!toHubManagerId.equals(managerId) && !fromHManagerId.equals(managerId)) {
//            throw new AuthException(ExceptionStatus.AUTHORIZATION_FAILED);
//        }
    }


    public void validateDeliveryHubManager(UUID deliveryId) {
        if (getUserRoleFromHeader() == UserRole.MANAGER)
            return;
        UUID managerId = getUserIdFromHeader();
        Delivery delivery = deliveryRepository.getById(deliveryId);

        //TODO: HubManager 정보 받아오기
        //UUID toHubManagerId = hubClient.getMangerIdByHubId(track.getFromHubId());
        //UUID fromHManagerId = hubClient.getMangerIdByHubId(track.getFromHubId());

//        if (!toHubManagerId.equals(managerId) && !fromHManagerId.equals(managerId)) {
//            throw new AuthException(ExceptionStatus.AUTHORIZATION_FAILED);
//        }
    }


    public void validateDeliveryShipper(UUID deliveryId) {
        if (getUserRoleFromHeader() == UserRole.SHIPPER)
            return;

        UUID shipperId = getUserIdFromHeader();
        Delivery delivery = deliveryRepository.getById(deliveryId);

        if(delivery.getTracks().stream().map(track -> track.getShipper().getId()).anyMatch(shipperId::equals))
            return;
        throw new AuthException(ExceptionStatus.AUTHORIZATION_FAILED);
    }

    public UUID getUserIdFromHeader() {
        try {
            return UUID.fromString(request.getHeader("X-User-Id"));
        } catch (IllegalArgumentException e) {
            throw new AuthException(ExceptionStatus.AUTHORIZATION_FAILED);
        }
    }

    public UserRole getUserRoleFromHeader() {
        try {
            return UserRole.valueOf(request.getHeader("X-User-Role"));
        } catch (IllegalArgumentException e) {
            throw new AuthException(ExceptionStatus.AUTHORIZATION_FAILED);
        }
    }

}