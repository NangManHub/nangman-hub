package com.nangman.delivery.application.service;

import com.nangman.delivery.application.dto.request.TrackCompletionPatchRequest;
import com.nangman.delivery.application.dto.request.TrackPutRequest;
import com.nangman.delivery.application.dto.request.TrackSearchRequest;
import com.nangman.delivery.application.dto.response.TrackResponse;
import com.nangman.delivery.domain.entity.QTrack;
import com.nangman.delivery.domain.entity.Track;
import com.nangman.delivery.domain.repository.TrackRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;

    @Transactional
    public TrackResponse updateTrack(UUID trackId, TrackPutRequest request) {
        Track track = trackRepository.getById(trackId);

        track.update(request.sequence(), request.shipperId(), request.fromHubId(), request.toHubId(), request.address(),
                request.expectDistance(), request.expectTime(), request.actualDistance(), request.actualTime(),
                request.status(), request.departureTime());

        return TrackResponse.from(track);
    }

    @Transactional
    public TrackResponse completeTrack(UUID trackId, TrackCompletionPatchRequest request) {
        Track track = trackRepository.getById(trackId);

        track.complete(request.actualDistance());

        return TrackResponse.from(track);
    }

    @Transactional
    public TrackResponse departureTrack(UUID trackId) {
        Track track = trackRepository.getById(trackId);

        track.departureTrack();

        return TrackResponse.from(track);
    }

    public Page<TrackResponse> searchTrack(TrackSearchRequest searchRequest, Pageable pageable) {
        QTrack track = QTrack.track;

        Predicate predicate = ExpressionUtils.allOf(
                track.isDelete.isFalse(),
                searchRequest.deliveryId() != null ? track.delivery.id.eq(searchRequest.deliveryId()) : null,
                searchRequest.sequence() != null ? track.sequence.eq(searchRequest.sequence()) : null,
                searchRequest.shipperId() != null ? track.shipperId.eq(searchRequest.shipperId()) : null,
                searchRequest.fromHubId() != null ? track.fromHubId.eq(searchRequest.fromHubId()) : null,
                searchRequest.toHubId() != null ? track.toHubId.eq(searchRequest.toHubId()) : null,
                searchRequest.address() != null ? track.address.contains(searchRequest.address()) : null,
                searchRequest.expectDistance() != null ? track.expectDistance.eq(searchRequest.expectDistance()) : null,
                searchRequest.expectTime() != null ? track.expectTime.eq(searchRequest.expectTime()) : null,
                searchRequest.actualDistance() != null ? track.actualDistance.eq(searchRequest.actualDistance()) : null,
                searchRequest.actualTime() != null ? track.actualTime.eq(searchRequest.actualTime()) : null,
                searchRequest.status() != null ? track.status.eq(searchRequest.status()) : null,
                searchRequest.departureTime() != null ? track.departureTime.eq(searchRequest.departureTime()) : null
        );

        return trackRepository.findAll(Objects.requireNonNull(predicate), pageable).map(TrackResponse::from);
    }
}
