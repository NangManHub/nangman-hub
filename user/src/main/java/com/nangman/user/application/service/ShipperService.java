package com.nangman.user.application.service;

import com.nangman.user.application.dto.kafka.ActionType;
import com.nangman.user.application.dto.kafka.ShipperEvent;
import com.nangman.user.application.dto.kafka.ShipperMessage;
import com.nangman.user.application.dto.request.ShipperPostRequest;
import com.nangman.user.application.dto.request.ShipperPutRequest;
import com.nangman.user.application.dto.response.ShipperPostResponse;
import com.nangman.user.application.dto.response.ShipperPutResponse;
import com.nangman.user.common.exception.CustomException;
import com.nangman.user.common.exception.ExceptionType;
import com.nangman.user.common.util.AuthorizationUtils;
import com.nangman.user.domain.entity.Shipper;
import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.entity.UserRole;
import com.nangman.user.domain.repository.ShipperRepository;
import com.nangman.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShipperService {

    private final UserRepository userRepository;
    private final ShipperRepository shipperRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthorizationUtils authorizationUtils;

    @Transactional
    public ShipperPostResponse createShipper(UUID reqUserId, ShipperPostRequest shipperPostRequest) {

        authorizationUtils.verifyShipperManagePermission(reqUserId, shipperPostRequest.hubId());

        User shipper = userRepository.findUser(shipperPostRequest.userId());
        if(shipper.getRole() != UserRole.SHIPPER) throw new CustomException(ExceptionType.ONLY_SHIPPER_REGISTERED);

        if(shipperRepository.findById(shipperPostRequest.userId()).isPresent()) throw new CustomException(ExceptionType.SHIPPER_ALREADY_EXISTS);

        Shipper savedShipper = shipperRepository.save(shipperPostRequest.toEntity(shipper));

        eventPublisher.publishEvent(new ShipperEvent(savedShipper.getId(), ShipperMessage.of(ActionType.CREATE, savedShipper)));

        return ShipperPostResponse.from(savedShipper);
    }

    @Transactional
    public ShipperPutResponse updateShipper(UUID reqUserId, UUID shipperId, ShipperPutRequest shipperPutRequest) {

        authorizationUtils.verifyShipperManagePermission(reqUserId, shipperPutRequest.hubId());

        Shipper shipper = shipperRepository.findShipper(shipperId);

        shipper.update(shipperPutRequest.hubId(), shipperPutRequest.type());
        
        eventPublisher.publishEvent(new ShipperEvent(shipper.getId(), ShipperMessage.of(ActionType.UPDATE, shipper)));

        return ShipperPutResponse.from(shipper);
    }

    @Transactional
    public void deleteShipper(UUID reqUserId, UUID shipperId) {
        Shipper shipper = shipperRepository.findShipper(shipperId);

        authorizationUtils.verifyShipperManagePermission(reqUserId, shipper.getHubId());

        shipper.delete(reqUserId);

        eventPublisher.publishEvent(new ShipperEvent(shipper.getId(), ShipperMessage.of(ActionType.DELETE, shipper)));
    }
}
