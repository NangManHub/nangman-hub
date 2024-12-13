package com.nangman.delivery.presentation;

import com.nangman.delivery.application.dto.request.TrackCompletionPatchRequest;
import com.nangman.delivery.application.dto.request.TrackPutRequest;
import com.nangman.delivery.application.dto.request.TrackSearchRequest;
import com.nangman.delivery.application.dto.response.TrackResponse;
import com.nangman.delivery.application.service.TrackService;
import com.nangman.delivery.common.annotation.RoleCheck;
import com.nangman.delivery.domain.enums.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Track", description = "배송 경로 API")
@RestController
@RequestMapping("/deliveries/tracks")
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;

    @Operation(summary = "배송 경로 수정", description = "배송 경로를 수정합니다. Master만 수정 가능합니다.")
    @RoleCheck(role = {UserRole.MASTER})
    @PutMapping("/{trackId}")
    public ResponseEntity<TrackResponse> updateTrack(@PathVariable UUID trackId,
                                                     @Valid @RequestBody TrackPutRequest request) {
        TrackResponse trackResponse = trackService.updateTrack(trackId, request);

        return ResponseEntity.ok(trackResponse);
    }

    @Operation(summary = "배송 경로 검색", description = "배송 경로를 검색합니다. Master 미만 권한은 본인이 포함된 리소스만 응받받습니다.")
    @GetMapping
    public ResponseEntity<PagedModel<TrackResponse>> searchTrack(TrackSearchRequest searchRequest, @PageableDefault Pageable pageable) {
        Page<TrackResponse> trackResponses = trackService.searchTrack(searchRequest, pageable);

        return ResponseEntity.ok(new PagedModel<>(trackResponses));
    }

    @Operation(summary = "배송 경로 완료", description = "배송 경로를 완료상태로 바꿉니다. Master 미만 권한은 본인이 포함된 리소스만 수정 가능합니다.")
    @RoleCheck(role = {UserRole.MASTER, UserRole.MANAGER, UserRole.SHIPPER})
    @PatchMapping("/{trackId}/completion")
    public ResponseEntity<TrackResponse> completeTrack(@PathVariable UUID trackId,
                                                       @Valid @RequestBody TrackCompletionPatchRequest request) {
        TrackResponse trackResponse = trackService.completeTrack(trackId, request);

        return ResponseEntity.ok(trackResponse);
    }

    @Operation(summary = "배송 경로 출발", description = "배송 경로를 배송중 상태로 바꿉니다. Master 미만 권한은 본인이 포함된 리소스만 수정 가능합니다.")
    @RoleCheck(role = {UserRole.MASTER, UserRole.MANAGER, UserRole.SHIPPER})
    @PatchMapping("/{trackId}/departure")
    public ResponseEntity<TrackResponse> departureTrack(@PathVariable UUID trackId) {
        TrackResponse trackResponse = trackService.departureTrack(trackId);

        return ResponseEntity.ok(trackResponse);
    }

}
