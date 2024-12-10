package com.nangman.hub.domain.entity;

import com.nangman.hub.application.dto.HubPostRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_hubs")
public class Hub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private UUID managerId;

    @Builder
    public Hub(String name, String address, Double latitude, Double longitude, UUID managerId) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.managerId = managerId;
    }

    public void update(HubPostRequest postRequest) {
        this.name = postRequest.name();
        this.address = postRequest.address();
        this.latitude = postRequest.latitude();
        this.longitude = postRequest.longitude();
        this.managerId = postRequest.managerId();
    }
}
