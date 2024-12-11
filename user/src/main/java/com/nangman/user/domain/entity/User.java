package com.nangman.user.domain.entity;

import com.nangman.user.application.dto.request.UserPutRequest;
import com.nangman.user.application.dto.response.Hub;
import com.nangman.user.application.dto.response.UserGetResponse;
import com.nangman.user.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
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

    public void update(UserPutRequest userPutRequest, String encodedPassword) {
        this.username = userPutRequest.username();
        this.password = encodedPassword;
        this.name = userPutRequest.name();
        this.role = userPutRequest.role();
        this.slackId = userPutRequest.slackId();
    }
}
