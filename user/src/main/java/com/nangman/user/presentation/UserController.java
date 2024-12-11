package com.nangman.user.presentation;

import com.nangman.user.application.dto.request.UserPutRequest;
import com.nangman.user.application.service.UserService;
import com.nangman.user.domain.entity.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@RequestHeader("X-User-Id") UUID reqUserId, @PathVariable(name = "userId") UUID userId) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(reqUserId, userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@RequestHeader("X-User-Id") UUID reqUserId, @PathVariable(name = "userId") UUID userId, @RequestBody UserPutRequest userPutRequest){

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(reqUserId, userId, userPutRequest));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader("X-User-Id") UUID reqUserId, @PathVariable(name = "userId") UUID userId) {

        userService.deleteUser(reqUserId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
