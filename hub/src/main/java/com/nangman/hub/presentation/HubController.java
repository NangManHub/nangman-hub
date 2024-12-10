package com.nangman.hub.presentation;

import com.nangman.hub.application.dto.HubPostRequest;
import com.nangman.hub.application.dto.HubResponse;
import com.nangman.hub.application.dto.UserRole;
import com.nangman.hub.application.service.HubService;
import com.nangman.hub.common.exception.CustomException;
import com.nangman.hub.common.exception.ExceptionCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("hubs")
@RequiredArgsConstructor
@RestController
public class HubController {

    private final HubService hubService;

    @PostMapping
    public HubResponse createHub(@Valid @RequestBody HubPostRequest postRequest,
                                 @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        return hubService.createHub(postRequest);
    }

    //
//    @GetMapping
//    public Page<HubResponse> getHubs(@RequestParam HubSearchRequest searchRequest,
//                                     @RequestParam Pageable pageable) {
//        return hubService.getHubs(searchRequest, pageable);
//    }
//
    @GetMapping("{hubId}")
    public HubResponse getHubById(@PathVariable UUID hubId) {
        return hubService.getHubById(hubId);
    }

    @PutMapping("{hubId}")
    public HubResponse updateHub(@PathVariable UUID hubId,
                                 @Valid @RequestBody HubPostRequest postRequest,
                                 @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        return hubService.updateHub(hubId, postRequest);
    }

    @DeleteMapping("{hubId}")
    public void deleteHub(@PathVariable UUID hubId,
                          @RequestHeader(value = "X-User-Id") String userId,
                          @RequestHeader(value = "X-User-Role") String userRole) {
        checkRole(userRole);
        hubService.deleteHub(hubId, UUID.fromString(userId));
    }

    private void checkRole(String userRole) {
        if (!UserRole.MASTER.toString().equals(userRole)) {
            throw new CustomException(ExceptionCode.ROLE_NOT_MASTER);
        }
    }
}
