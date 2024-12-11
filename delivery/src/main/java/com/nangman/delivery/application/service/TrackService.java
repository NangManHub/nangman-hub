package com.nangman.delivery.application.service;

import com.nangman.delivery.application.dto.request.TrackCompletionPatchRequest;
import com.nangman.delivery.application.dto.request.TrackPutRequest;
import com.nangman.delivery.application.dto.response.TrackResponse;
import com.nangman.delivery.common.exception.ApplicationException;
import com.nangman.delivery.common.exception.ExceptionStatus;
import com.nangman.delivery.domain.entity.Track;
import com.nangman.delivery.domain.entity.TrackStatus;
import com.nangman.delivery.domain.repository.TrackRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;

    @Transactional
    public TrackResponse updateTrack(UUID trackId, TrackPutRequest request) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new ApplicationException(ExceptionStatus.TRACK_NOT_FOUND));

        track.update(request.sequence(), request.shipperId(), request.fromHubId(), request.toHubId(), request.address(),
                request.expectDistance(), request.expectTime(), request.actualDistance(), request.actualTime(),
                request.status(), request.departureTime());

        return TrackResponse.from(track);
    }

    @Transactional
    public TrackResponse completeTrack(UUID trackId, TrackCompletionPatchRequest request) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new ApplicationException(ExceptionStatus.TRACK_NOT_FOUND));

        if(track.getStatus() != TrackStatus.MOVING)
            throw new ApplicationException(ExceptionStatus.TRACK_NOT_MOVING);

        track.complete(request.actualDistance());

        return TrackResponse.from(track);
    }

    @Transactional
    public TrackResponse departureTrack(UUID trackId) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new ApplicationException(ExceptionStatus.TRACK_NOT_FOUND));

        if(track.getStatus() != TrackStatus.WAITING)
            throw new ApplicationException(ExceptionStatus.TRACK_NOT_WAITING);

        track.departureTrack();

        return TrackResponse.from(track);
    }
}
