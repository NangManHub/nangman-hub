package com.nangman.user.common.util;

import com.nangman.user.application.dto.HubDto;
import com.nangman.user.common.exception.CustomException;
import com.nangman.user.common.exception.ExceptionType;
import com.nangman.user.common.feign.HubClient;
import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.entity.UserRole;
import com.nangman.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthorizationUtils {

    private final HubClient hubClient;

    private final UserRepository userRepository;

    // 마스터 권한을 가지고 있는지 확인
    public void verifyMasterRole(UUID reqUserId){
        User user = userRepository.findByIdAndIsDeletedFalse(reqUserId).orElseThrow(() -> new CustomException(ExceptionType.MASTER_NOT_FOUND));
        if(user.getRole() != UserRole.MASTER) throw new CustomException(ExceptionType.MASTER_ROLE_REQUIRED);
    }

    // 배송담당자 관리 권한 검증 메서드
    public void verifyShipperManagePermission(UUID reqUserId, UUID hubId){
        UserRole reqUserRole = userRepository.findUser(reqUserId).getRole();

        if(reqUserRole != UserRole.MANAGER && reqUserRole != UserRole.MASTER) throw new CustomException(ExceptionType.SHIPPER_ACCESS_DENIED);

        HubDto hub = hubClient.getHub(hubId);

        if(reqUserRole == UserRole.MANAGER && !reqUserId.equals(hub.managerId())) throw new CustomException(ExceptionType.SHIPPER_ACCESS_DENIED);
    }

    // 유저 접근 권한 검증 메서드
    public void verifyUserAccessPermission(User reqUser, UUID userId){
        if(reqUser.getRole() != UserRole.MASTER && !reqUser.getId().equals(userId)) throw new CustomException(ExceptionType.USER_ACCESS_DENIED);
    }
}
