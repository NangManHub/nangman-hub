package com.nangman.user.application.service;

import com.nangman.user.application.dto.request.SigninRequest;
import com.nangman.user.application.dto.request.SignupRequest;
import com.nangman.user.application.dto.request.UserPutRequest;
import com.nangman.user.application.dto.response.SignupResponse;
import com.nangman.user.application.dto.response.UserGetResponse;
import com.nangman.user.application.dto.response.UserPutResponse;
import com.nangman.user.common.exception.CustomException;
import com.nangman.user.common.exception.ExceptionType;
import com.nangman.user.common.util.JwtUtil;
import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.entity.UserRole;
import com.nangman.user.domain.repository.UserQueryRepository;
import com.nangman.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final UserQueryRepository userQueryRepository;

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {

        if (userRepository.findByUsername(signupRequest.username()).isPresent()) {
            throw new CustomException(ExceptionType.DUPLICATE_USER_NAME);
        }

        User savedUser = userRepository.save(signupRequest.toEntity(passwordEncoder.encode(signupRequest.password())));
        return SignupResponse.from(savedUser);
    }

    public String signin(SigninRequest signinRequest) {

        // 회원 정보 조회
        User user = userRepository.findByUsername(signinRequest.username()).orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        // 탈퇴 여부 확인
        if(user.getIsDeleted()) throw new CustomException(ExceptionType.WITHDRAWN_USER_ACCOUNT);

        // 비밀번호 확인
        if(!passwordEncoder.matches(signinRequest.password(), user.getPassword())) throw new CustomException(ExceptionType.PASSWORD_NOT_MATCHED);

        // accessToken 반환
        return jwtUtil.createToken(user.getId(), user.getUsername() ,user.getRole());
    }

    public Page<UserGetResponse> search(Pageable pageable, List<UserRole> roles) {
        return userQueryRepository.searchUser(pageable, roles);
    }

    @Transactional
    public UserPutResponse updateUser(UUID reqUserId, UUID userId, UserPutRequest userPutRequest) {
        verifyMasterRole(reqUserId);

        User user = userRepository.findUser(userId);
        user.update(
                userPutRequest.username(),
                passwordEncoder.encode(userPutRequest.password()),
                userPutRequest.name(),
                userPutRequest.role(),
                userPutRequest.slackId()
        );

        return UserPutResponse.from(user);
    }

    public void verifyMasterRole(UUID reqUserId){
        User user = userRepository.findByIdAndIsDeletedFalse(reqUserId).orElseThrow(() -> new CustomException(ExceptionType.MASTER_NOT_FOUND));
        if(user.getRole() != UserRole.MASTER) throw new CustomException(ExceptionType.MASTER_ROLE_REQUIRED);
    }
}
