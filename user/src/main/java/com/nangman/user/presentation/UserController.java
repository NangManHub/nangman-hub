package com.nangman.user.presentation;

import com.nangman.user.application.service.UserService;
import com.nangman.user.domain.entity.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> searchUser(Pageable pageable, @RequestParam(required = false) List<UserRole> roles) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.search(pageable, roles));
    }
}
