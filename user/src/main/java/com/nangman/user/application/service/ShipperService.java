package com.nangman.user.application.service;

import com.nangman.user.application.dto.HubDto;
import com.nangman.user.application.dto.request.ShipperPostRequest;
import com.nangman.user.application.dto.request.ShipperPutRequest;
import com.nangman.user.application.dto.response.ShipperPostResponse;
import com.nangman.user.application.dto.response.ShipperPutResponse;
import com.nangman.user.common.exception.CustomException;
import com.nangman.user.common.exception.ExceptionType;
import com.nangman.user.common.feign.HubClient;
import com.nangman.user.domain.entity.Shipper;
import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.entity.UserRole;
import com.nangman.user.domain.repository.ShipperRepository;
import com.nangman.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final HubClient hubClient;

    @Transactional
    public ShipperPostResponse createShipper(UUID reqUserId, ShipperPostRequest shipperPostRequest) {

        verifyRole(reqUserId, shipperPostRequest.hubId());

        User shipper = userRepository.findUser(shipperPostRequest.userId());
        if(shipper.getRole() != UserRole.SHIPPER) throw new CustomException(ExceptionType.ONLY_SHIPPER_REGISTERED);

        Shipper savedShipper = shipperRepository.save(shipperPostRequest.toEntity(shipper));
        return ShipperPostResponse.from(savedShipper);
    }

    @Transactional
    public ShipperPutResponse updateShipper(UUID reqUserId, UUID shipperId, ShipperPutRequest shipperPutRequest) {

        verifyRole(reqUserId, shipperPutRequest.hubId());

        Shipper shipper = shipperRepository.findShipper(shipperId);

        hubClient.getHub(shipperPutRequest.hubId());

        shipper.update(shipperPutRequest.hubId(), shipperPutRequest.type());

        return ShipperPutResponse.from(shipper);
    }

    private void verifyRole(UUID reqUserId, UUID hubId){
        UserRole reqUserRole = userRepository.findUser(reqUserId).getRole();

        if(reqUserRole != UserRole.MANAGER && reqUserRole != UserRole.MASTER) throw new CustomException(ExceptionType.SHIPPER_ACCESS_DENIED);

        if(reqUserRole == UserRole.MANAGER){
//            HubDto hubByManagerId = hubClient.getHubByManagerId(reqUserId);
//
//            log.info("{}", hubByManagerId.hubId());
//            log.info("{}", hubByManagerId.managerId());
//
//            if(hubByManagerId.hubId() != hubId) throw new CustomException(ExceptionType.SHIPPER_ACCESS_DENIED);
        }
    }
}
