package com.nangman.user.presentation;

import com.nangman.user.application.dto.request.UserPutRequest;
import com.nangman.user.application.dto.response.UserGetResponse;
import com.nangman.user.application.dto.response.UserPutResponse;
import com.nangman.user.application.service.UserService;
import com.nangman.user.domain.entity.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "User", description = "User API")
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 검색 API", description = "마스터가 유저를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 검색 성공", content = @Content(schema = @Schema(implementation = UserGetResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 자원에 대한 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 마스터입니다.")
    })
    @GetMapping
    public ResponseEntity<?> searchUser(@RequestHeader("X-User-Id") UUID reqUserId,Pageable pageable, @RequestParam(required = false) List<UserRole> roles) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.search(reqUserId, pageable, roles));
    }

    @Operation(summary = "유저 단건 조회 API", description = "유저가 단건 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 검색 성공", content = @Content(schema = @Schema(implementation = UserGetResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 자원에 대한 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다.")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@RequestHeader("X-User-Id") UUID reqUserId, @PathVariable(name = "userId") UUID userId) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(reqUserId, userId));
    }

    @Operation(summary = "유저 수정 API", description = "유저를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 수정 성공", content = @Content(schema = @Schema(implementation = UserPutResponse.class))),
            @ApiResponse(responseCode = "403", description = "마스터 권한이 필요합니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다. / 중복된 아이디 입니다.")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@RequestHeader("X-User-Id") UUID reqUserId, @PathVariable(name = "userId") UUID userId, @RequestBody UserPutRequest userPutRequest){

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(reqUserId, userId, userPutRequest));
    }

    @Operation(summary = "유저 삭제 API", description = "유저를 논리적 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "유저 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "마스터 권한이 필요합니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다.")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader("X-User-Id") UUID reqUserId, @PathVariable(name = "userId") UUID userId) {

        userService.deleteUser(reqUserId, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
