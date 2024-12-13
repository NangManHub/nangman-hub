package com.nangman.user.domain.repository;

import com.nangman.user.common.exception.CustomException;
import com.nangman.user.common.exception.ExceptionType;
import com.nangman.user.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository {
    User save(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndIsDeletedFalse(UUID userID);

    default User findUser(UUID userId){
        return findByIdAndIsDeletedFalse(userId).orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
    }
}
