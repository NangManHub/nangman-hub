package com.nangman.hub.presentation;

import com.nangman.hub.application.dto.request.HubPostRequest;
import com.nangman.hub.application.dto.response.HubResponse;
import com.nangman.hub.application.dto.request.HubSearchRequest;
import com.nangman.hub.application.dto.UserRole;
import com.nangman.hub.application.service.HubService;
import com.nangman.hub.common.exception.CustomException;
import com.nangman.hub.common.exception.ExceptionCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("hubs")
@RequiredArgsConstructor
@RestController
public class HubController {

    private final HubService hubService;

    @PostMapping
    public ResponseEntity<HubResponse> createHub(@Valid @RequestBody HubPostRequest postRequest,
                                                 @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(hubService.createHub(postRequest));
    }

    @GetMapping
    public ResponseEntity<Page<HubResponse>> getHubs(@ModelAttribute HubSearchRequest searchRequest,
                                                     @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(hubService.getHubs(searchRequest, pageable));
    }

    @GetMapping("{hubId}")
    public ResponseEntity<HubResponse> getHubById(@PathVariable UUID hubId) {
        return ResponseEntity.ok(hubService.getHubById(hubId));
    }

    @PutMapping("{hubId}")
    public ResponseEntity<HubResponse> updateHub(@PathVariable UUID hubId,
                                                 @Valid @RequestBody HubPostRequest postRequest,
                                                 @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        return ResponseEntity.ok(hubService.updateHub(hubId, postRequest));
    }

    @DeleteMapping("{hubId}")
    public ResponseEntity<Void> deleteHub(@PathVariable UUID hubId,
                                          @RequestHeader(value = "X-User-Id") String userId,
                                          @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        hubService.deleteHub(hubId, UUID.fromString(userId));
        return ResponseEntity.noContent().build();
    }

    private void checkRole(String userRole) {
        if (!UserRole.MASTER.toString().equals(userRole)) {
            throw new CustomException(ExceptionCode.ROLE_NOT_MASTER);
        }
    }
}
