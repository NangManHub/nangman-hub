package com.nangman.user.application.service;

import com.nangman.user.application.dto.request.SignupRequest;
import com.nangman.user.application.dto.response.SignupResponse;
import com.nangman.user.common.exception.CustomException;
import com.nangman.user.common.exception.ExceptionType;
import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignupResponse singUp(SignupRequest signupRequest) {

        if (userRepository.findByUsername(signupRequest.username()).isPresent()) {
            throw new CustomException(ExceptionType.DUPLICATE_USER_NAME);
        }

        User savedUser = userRepository.save(signupRequest.toEntity());
        return SignupResponse.from(savedUser);
    }
}
