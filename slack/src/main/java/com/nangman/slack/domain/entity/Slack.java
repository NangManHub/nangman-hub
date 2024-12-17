package com.nangman.slack.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_slacks")
public class Slack extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "message", nullable = false, columnDefinition="TEXT")
    private String message;

    @Column(name = "send_at", nullable = false)
    private LocalDateTime sendAt;

    @Column(name = "receiver_id", nullable = false, length = 20)
    private String receiverId;

}