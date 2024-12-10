package com.nangman.user.presentation;

import com.nangman.user.application.dto.request.SigninRequest;
import com.nangman.user.application.dto.request.SignupRequest;
import com.nangman.user.application.service.UserService;
import com.nangman.user.common.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/singup")
    public ResponseEntity<?> singup(@RequestBody SignupRequest signupRequest){

        log.info("username : {}", signupRequest.username());
        log.info("password : {}", signupRequest.password());
        log.info("name : {}", signupRequest.name());
        log.info("role : {}", signupRequest.role());
        log.info("slackId : {}", signupRequest.slackId());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.singUp(signupRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest, HttpServletResponse response) {

        log.info(signinRequest.username());
        log.info(signinRequest.password());

        String accessToken = userService.signin(signinRequest);

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
