package com.nangman.user.domain.repository;

import com.nangman.user.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository {
    User save(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(UUID userId);
}
