package com.nangman.delivery.domain.entity;

import com.nangman.delivery.common.exception.DomainException;
import com.nangman.delivery.common.exception.ExceptionStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_delivery")
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DeliveryStatus status;

    @Column(name = "from_hub_id", nullable = false)
    private UUID fromHubId;

    @Column(name = "to_hub_id")
    private UUID toHubId;

    @Column(name="address", nullable = false, length = 50)
    private String address;

    @Column(name="recipient", nullable = false)
    private UUID recipient;

    @Column(name="order_id", updatable = false, nullable = false)
    private UUID orderId;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
    private List<Track> tracks = new ArrayList<>();

    @Builder
    public Delivery(UUID fromHubId, UUID toHubId, String address, UUID recipient, UUID orderId) {
        this.fromHubId = fromHubId;
        this.toHubId = toHubId;
        this.address = address;
        this.recipient = recipient;
        this.orderId = orderId;
        this.status = DeliveryStatus.WAITING;
    }

    public void updateTrack(Track track) {
        if (this.tracks.stream().anyMatch(t -> t.getSequence() == track.getSequence())) {
            throw new DomainException(ExceptionStatus.TRACK_SEQUENCE_DUPLICATED);
        }
        this.tracks.add(track);
    }

    public void updateTracks(List<Track> tracks) {
        Set<Integer> sequences = tracks.stream().map(Track::getSequence).collect(Collectors.toSet());
        tracks.forEach(track -> {
            if (sequences.contains(track.getSequence())) {
                throw new DomainException(ExceptionStatus.TRACK_SEQUENCE_DUPLICATED);
            }
            sequences.add(track.getSequence());
        });
        this.tracks.addAll(tracks);
    }
}
