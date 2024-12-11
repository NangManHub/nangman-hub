package com.nangman.user.infrastructure;

import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepositoryImpl extends JpaRepository<User, UUID>, UserRepository {
}
