package com.nangman.delivery.presentation;

import com.nangman.delivery.application.dto.request.TrackCompletionPatchRequest;
import com.nangman.delivery.application.dto.request.TrackPutRequest;
import com.nangman.delivery.application.dto.request.TrackSearchRequest;
import com.nangman.delivery.application.dto.response.TrackResponse;
import com.nangman.delivery.application.service.TrackService;
import com.nangman.delivery.common.annotation.RoleCheck;
import com.nangman.delivery.domain.enums.UserRole;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @RoleCheck(role = {UserRole.MASTER})
    @PutMapping("/{trackId}")
    public ResponseEntity<TrackResponse> updateTrack(@PathVariable UUID trackId,
                                                     @Valid @RequestBody TrackPutRequest request) {
        TrackResponse trackResponse = trackService.updateTrack(trackId, request);

        return ResponseEntity.ok(trackResponse);
    }

    @GetMapping
    public ResponseEntity<PagedModel<TrackResponse>> searchTrack(TrackSearchRequest searchRequest, @PageableDefault Pageable pageable) {
        Page<TrackResponse> trackResponses = trackService.searchTrack(searchRequest, pageable);

        return ResponseEntity.ok(new PagedModel<>(trackResponses));
    }

    @RoleCheck(role = {UserRole.MASTER})
    @PatchMapping("/{trackId}/completion")
    public ResponseEntity<TrackResponse> completeTrack(@PathVariable UUID trackId,
                                                       @Valid @RequestBody TrackCompletionPatchRequest request) {
        TrackResponse trackResponse = trackService.completeTrack(trackId, request);

        return ResponseEntity.ok(trackResponse);
    }

    @RoleCheck(role = {UserRole.MASTER})
    @PatchMapping("/{trackId}/departure")
    public ResponseEntity<TrackResponse> departureTrack(@PathVariable UUID trackId) {
        TrackResponse trackResponse = trackService.departureTrack(trackId);

        return ResponseEntity.ok(trackResponse);
    }

}
