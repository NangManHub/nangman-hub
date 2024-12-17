package com.nangman.hub.presentation;

import com.nangman.hub.application.dto.UserRole;
import com.nangman.hub.application.dto.request.RoutePostRequest;
import com.nangman.hub.application.dto.request.RouteSearchRequest;
import com.nangman.hub.application.dto.response.RouteDetailResponse;
import com.nangman.hub.application.dto.response.RouteResponse;
import com.nangman.hub.application.service.RouteService;
import com.nangman.hub.common.exception.CustomException;
import com.nangman.hub.common.exception.ExceptionCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Route", description = "Route API")
@RequestMapping("hubs/routes")
@RequiredArgsConstructor
@RestController
public class RouteController {

    private final RouteService routeService;

    @Operation(summary = "경로 생성 API", description = "마스터가 경로를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "경로 생성 성공", content = @Content(schema = @Schema(implementation = RouteResponse.class))),
            @ApiResponse(responseCode = "403", description = "마스터 권한이 필요합니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 허브입니다.")
    })
    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@Valid @RequestBody RoutePostRequest postRequest,
                                                     @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(routeService.createRoute(postRequest));
    }

    @Operation(summary = "경로 목록 조회 API", description = "경로 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경로 목록 조회 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RouteResponse.class)))),
    })
    @GetMapping
    public ResponseEntity<Page<RouteResponse>> getRoutes(@ModelAttribute RouteSearchRequest searchRequest,
                                                         @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(routeService.getRoutes(searchRequest, pageable));
    }

    @Operation(summary = "최적 경로 조회 API", description = "최적 경로를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최적 경로 목록 조회 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RouteResponse.class)))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 허브입니다.")
    })
    @GetMapping("best")
    public ResponseEntity<List<RouteDetailResponse>> getBestRoutes(@RequestParam UUID fromHubId,
                                                                   @RequestParam UUID toHubId) {
        return ResponseEntity.ok(routeService.getBestRoutes(fromHubId, toHubId));
    }

    @Operation(summary = "경로 조회 API", description = "경로를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경로 조회 성공", content = @Content(schema = @Schema(implementation = RouteResponse.class))),
    })
    @GetMapping("{routeId}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable UUID routeId) {
        return ResponseEntity.ok(routeService.getRouteById(routeId));
    }

    @Operation(summary = "경로 수정 API", description = "마스터가 경로를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경로 수정 성공", content = @Content(schema = @Schema(implementation = RouteResponse.class))),
            @ApiResponse(responseCode = "403", description = "마스터 권한이 필요합니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 경로입니다. / 존재하지 않는 허브입니다.")
    })
    @PutMapping("{routeId}")
    public ResponseEntity<RouteResponse> updateRoute(@PathVariable UUID routeId,
                                                     @Valid @RequestBody RoutePostRequest postRequest,
                                                     @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        return ResponseEntity.ok(routeService.updateRoute(routeId, postRequest));
    }

    @Operation(summary = "경로 삭제 API", description = "마스터가 경로를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "허브 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "마스터 권한이 필요합니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 경로입니다.")
    })
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
