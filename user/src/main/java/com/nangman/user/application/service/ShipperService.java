package com.nangman.user.application.service;

import com.nangman.user.application.dto.request.ShipperPostRequest;
import com.nangman.user.application.dto.response.ShipperPostResponse;
import com.nangman.user.common.exception.CustomException;
import com.nangman.user.common.exception.ExceptionType;
import com.nangman.user.domain.entity.Shipper;
import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.entity.UserRole;
import com.nangman.user.domain.repository.ShipperRepository;
import com.nangman.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShipperService {

    private final UserRepository userRepository;
    private final ShipperRepository shipperRepository;

    @Transactional
    public ShipperPostResponse createShipper(UUID reqUserId, ShipperPostRequest shipperPostRequest) {

        verifyRole(reqUserId);
        User user = userRepository.findById(shipperPostRequest.userId()).orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        if(user.getRole() != UserRole.SHIPPER) throw new CustomException(ExceptionType.INTERNAL_SERVER_ERROR);
        Shipper savedShipper = shipperRepository.save(shipperPostRequest.toEntity(user));
        return ShipperPostResponse.from(savedShipper);
    }

    private void verifyRole(UUID reqUserId){
        // TODO 권한 확인 (마스터 관리자 OR 담당 허브 관리자면 OK)
        User reqUser = userRepository.findById(reqUserId).orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        UserRole reqUserRole = reqUser.getRole();

        if(reqUserRole != UserRole.MANAGER && reqUserRole != UserRole.MASTER) throw new CustomException(ExceptionType.INTERNAL_SERVER_ERROR);

        if(reqUserRole == UserRole.MANAGER){ // 담당 허브관리자 확인 로직
        }

    }
}
