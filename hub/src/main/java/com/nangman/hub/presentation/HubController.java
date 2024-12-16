package com.nangman.hub.presentation;

import com.nangman.hub.application.dto.UserRole;
import com.nangman.hub.application.dto.request.HubPostRequest;
import com.nangman.hub.application.dto.request.HubSearchRequest;
import com.nangman.hub.application.dto.response.HubResponse;
import com.nangman.hub.application.service.HubService;
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

import java.util.UUID;

@Tag(name = "Hub", description = "Hub API")
@RequestMapping("hubs")
@RequiredArgsConstructor
@RestController
public class HubController {

    private final HubService hubService;

    @Operation(summary = "허브 생성 API", description = "마스터가 허브를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "허브 생성 성공", content = @Content(schema = @Schema(implementation = HubResponse.class))),
            @ApiResponse(responseCode = "403", description = "마스터 권한이 필요합니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 매니저입니다.")
    })
    @PostMapping
    public ResponseEntity<HubResponse> createHub(@Valid @RequestBody HubPostRequest postRequest,
                                                 @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(hubService.createHub(postRequest));
    }

    @Operation(summary = "허브 목록 조회 API", description = "허브 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "허브 목록 조회 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HubResponse.class)))),
    })
    @GetMapping
    public ResponseEntity<Page<HubResponse>> getHubs(@ModelAttribute HubSearchRequest searchRequest,
                                                     @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(hubService.getHubs(searchRequest, pageable));
    }

    @Operation(summary = "허브 조회 API", description = "허브를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "허브 조회 성공", content = @Content(schema = @Schema(implementation = HubResponse.class))),
    })
    @GetMapping("{hubId}")
    public ResponseEntity<HubResponse> getHubById(@PathVariable UUID hubId) {
        return ResponseEntity.ok(hubService.getHubById(hubId));
    }

    @Operation(summary = "허브 수정 API", description = "마스터가 허브를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "허브 수정 성공", content = @Content(schema = @Schema(implementation = HubResponse.class))),
            @ApiResponse(responseCode = "403", description = "마스터 권한이 필요합니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 매니저입니다. / 존재하지 않는 허브입니다.")
    })
    @PutMapping("{hubId}")
    public ResponseEntity<HubResponse> updateHub(@PathVariable UUID hubId,
                                                 @Valid @RequestBody HubPostRequest postRequest,
                                                 @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        return ResponseEntity.ok(hubService.updateHub(hubId, postRequest));
    }

    @Operation(summary = "허브 삭제 API", description = "마스터가 허브를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "허브 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "마스터 권한이 필요합니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 허브입니다.")
    })
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
