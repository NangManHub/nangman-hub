package com.nangman.hub.presentation;

import com.nangman.hub.application.dto.UserRole;
import com.nangman.hub.application.dto.request.RoutePostRequest;
import com.nangman.hub.application.dto.request.RouteSearchRequest;
import com.nangman.hub.application.dto.response.RouteDetailResponse;
import com.nangman.hub.application.dto.response.RouteResponse;
import com.nangman.hub.application.service.RouteService;
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

import java.util.List;
import java.util.UUID;

@RequestMapping("hubs/routes")
@RequiredArgsConstructor
@RestController
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@Valid @RequestBody RoutePostRequest postRequest,
                                                 @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(routeService.createRoute(postRequest));
    }

    @GetMapping
    public ResponseEntity<Page<RouteResponse>> getRoutes(@ModelAttribute RouteSearchRequest searchRequest,
                                                         @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(routeService.getRoutes(searchRequest, pageable));
    }

    @GetMapping("best")
    public ResponseEntity<List<RouteDetailResponse>> getBestRoutes(@RequestParam UUID fromHubId,
                                                                   @RequestParam UUID toHubId) {
        return ResponseEntity.ok(routeService.getBestRoutes(fromHubId, toHubId));
    }

    @GetMapping("{routeId}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable UUID routeId) {
        return ResponseEntity.ok(routeService.getRouteById(routeId));
    }

    @PutMapping("{routeId}")
    public ResponseEntity<RouteResponse> updateRoute(@PathVariable UUID routeId,
                                                 @Valid @RequestBody RoutePostRequest postRequest,
                                                 @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        return ResponseEntity.ok(routeService.updateRoute(routeId, postRequest));
    }

    @DeleteMapping("{routeId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable UUID routeId,
                                          @RequestHeader(value = "X-User-Id") String userId,
                                          @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        routeService.deleteRoute(routeId, UUID.fromString(userId));
        return ResponseEntity.noContent().build();
    }

    private void checkRole(String userRole) {
        if (!UserRole.MASTER.toString().equals(userRole)) {
            throw new CustomException(ExceptionCode.ROLE_NOT_MASTER);
        }
    }
}
