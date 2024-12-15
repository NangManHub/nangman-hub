package com.nangman.hub.application.service;

import com.nangman.hub.application.dto.UserResponse;

import java.util.UUID;

public interface UserService {
    UserResponse getUserById(UUID userId);
}
