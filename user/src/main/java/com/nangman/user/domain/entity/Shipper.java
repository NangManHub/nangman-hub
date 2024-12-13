package com.nangman.user.domain.entity;

import com.nangman.user.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_shippers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Shipper extends BaseEntity {

    @Id
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private User user;

    @Column(nullable = false)
    private UUID hubId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipperType type;

    public void update(UUID hubId, ShipperType type) {
        this.hubId = hubId;
        this.type =type;
    }
}
