package com.nangman.delivery.presentation;

import com.nangman.delivery.application.dto.request.TrackCompletionPatchRequest;
import com.nangman.delivery.application.dto.request.TrackPutRequest;
import com.nangman.delivery.application.dto.response.TrackResponse;
import com.nangman.delivery.application.service.TrackService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries/tracks")
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;

    @PutMapping("/{trackId}")
    public ResponseEntity<TrackResponse> updateTrack(@PathVariable UUID trackId,
                                                     @Valid @RequestBody TrackPutRequest request) {
        TrackResponse trackResponse = trackService.updateTrack(trackId, request);

        return ResponseEntity.ok(trackResponse);
    }

    @PatchMapping("/{trackId}/completion")
    public ResponseEntity<TrackResponse> completeTrack(@PathVariable UUID trackId,
                                                       @Valid @RequestBody TrackCompletionPatchRequest request) {
        TrackResponse trackResponse = trackService.completeTrack(trackId, request);

        return ResponseEntity.ok(trackResponse);
    }

    @PatchMapping("/{trackId}/departure")
    public ResponseEntity<TrackResponse> departureTrack(@PathVariable UUID trackId) {
        TrackResponse trackResponse = trackService.departureTrack(trackId);

        return ResponseEntity.ok(trackResponse);
    }

}
