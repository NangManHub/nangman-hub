package com.nangman.user.domain.entity;

import com.nangman.user.application.dto.request.SignupRequest;
import com.nangman.user.application.dto.response.Hub;
import com.nangman.user.application.dto.response.UserGetResponse;
import com.nangman.user.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_users")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 10, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(length = 10, nullable = false)
    private String slackId;

    public static User create(SignupRequest signupRequest, String encodePassword) {
        return User.builder()
                .username(signupRequest.username())
                .password(encodePassword)
                .name(signupRequest.name())
                .role(signupRequest.role())
                .slackId(signupRequest.slackId())
                .build();
    }

    public UserGetResponse toResponseDto() {
        return UserGetResponse.of(
                this.id,
                this.username,
                this.name,
                this.role,
                this.slackId,
                Hub.of(UUID.randomUUID(), "hub명", "hub주소", 1.0, 1.0)
        );
    }
}
