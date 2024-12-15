package com.nangman.delivery.domain.entity;

import com.nangman.delivery.domain.enums.ShipperType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_shippers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shipper {

    @Id
    private UUID id;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    @Column(name = "total_distance", nullable = false)
    private Long totalDistance;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipper_type", nullable = false)
    private ShipperType shipperType;

    @OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL)
    private List<Track> tracks;

    @Builder
    private Shipper(UUID id, UUID hubId, ShipperType shipperType) {
        this.id = id;
        this.hubId = hubId;
        this.totalDistance = 0L;
        this.shipperType = shipperType;
    }

    public void update(UUID hubId, ShipperType type) {
        this.hubId = hubId;
        this.shipperType = type;
    }
}
