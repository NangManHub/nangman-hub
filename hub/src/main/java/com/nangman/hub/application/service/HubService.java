package com.nangman.hub.application.service;

import com.nangman.hub.application.dto.UserResponse;
import com.nangman.hub.application.dto.request.HubPostRequest;
import com.nangman.hub.application.dto.request.HubSearchRequest;
import com.nangman.hub.application.dto.response.HubResponse;
import com.nangman.hub.common.exception.CustomException;
import com.nangman.hub.common.exception.ExceptionCode;
import com.nangman.hub.domain.entity.Hub;
import com.nangman.hub.domain.entity.QHub;
import com.nangman.hub.domain.repository.HubRepository;
import com.querydsl.core.BooleanBuilder;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class HubService {

    private final HubRepository hubRepository;
    private final RouteService routeService;
    private final UserService userService;

    public HubResponse createHub(HubPostRequest postRequest) {
        checkManager(postRequest.managerId());

        Hub parentHub = postRequest.parentHubId() != null ? hubRepository.findHub(postRequest.parentHubId()) : null;
        Hub hub = hubRepository.save(postRequest.toEntity(parentHub));

        // create routes
        if (parentHub != null) {
            // hub <-> central hub
            routeService.createRouteWithHub(hub, parentHub);
        } else {
            // hub <-> other central hubs
            hubRepository.findAllByParentHubIdIsNullAndIdNotAndIsDeleteFalse(hub.getId())
                    .forEach(otherHub -> routeService.createRouteWithHub(hub, otherHub));
        }

        return HubResponse.from(hub);
    }

    @Transactional(readOnly = true)
    public Page<HubResponse> getHubs(HubSearchRequest searchRequest, Pageable pageable) {
        BooleanBuilder qBuilder = new BooleanBuilder();
        qBuilder.and(QHub.hub.isDelete.isFalse());
        if (searchRequest.name() != null) {
            qBuilder.and(QHub.hub.name.contains(searchRequest.name()));
        }
        if (searchRequest.address() != null) {
            qBuilder.and(QHub.hub.address.contains(searchRequest.address()));
        }
        if (searchRequest.latitude() != null) {
            qBuilder.and(QHub.hub.latitude.goe(searchRequest.latitude()));
        }
        if (searchRequest.longitude() != null) {
            qBuilder.and(QHub.hub.longitude.goe(searchRequest.longitude()));
        }
        if (searchRequest.managerId() != null) {
            qBuilder.and(QHub.hub.managerId.eq(searchRequest.managerId()));
        }
        if (searchRequest.parentHubId() != null) {
            qBuilder.and(QHub.hub.parentHub.id.eq(searchRequest.parentHubId()));
        }
        return hubRepository.findAll(qBuilder, pageable).map(HubResponse::from);
    }

    @Transactional(readOnly = true)
    public HubResponse getHubById(UUID hubId) {
        return HubResponse.from(hubRepository.findHub(hubId));
    }

    public HubResponse updateHub(UUID hubId, HubPostRequest postRequest) {
        Hub hub = hubRepository.findHub(hubId);

        // manager 확인
        if (hub.getManagerId() != postRequest.managerId()) {
            checkManager(postRequest.managerId());
        }
        // parentHub 확인
        Hub newParentHub = null;
        UUID newParentHubId = postRequest.parentHubId();
        if (newParentHubId != null) {
            Hub curParentHub = hub.getParentHub();
            newParentHub = curParentHub.getId() != newParentHubId ? hubRepository.findHub(newParentHubId) : curParentHub;
        }
        hub.update(
                postRequest.name(),
                postRequest.address(),
                postRequest.latitude(),
                postRequest.longitude(),
                postRequest.managerId(),
                newParentHub
        );
        return HubResponse.from(hub);
    }

    public void deleteHub(UUID hubId, UUID userId) {
        Hub hub = hubRepository.findHub(hubId);
        hub.delete(userId);
    }

    private void checkManager(UUID managerId) {
        try {
            UserResponse user = userService.getUserById(managerId);
        } catch (FeignException e) {
            if (e.status() == HttpStatus.BAD_REQUEST.value()) {
                throw new CustomException(ExceptionCode.MANAGER_NOT_FOUND, e);
            }
            throw new CustomException(ExceptionCode.COMMON_SERVER_ERROR, e);
        }
    }
}
