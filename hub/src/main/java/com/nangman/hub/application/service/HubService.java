package com.nangman.hub.application.service;

import com.nangman.hub.application.dto.HubPostRequest;
import com.nangman.hub.application.dto.HubResponse;
import com.nangman.hub.application.dto.HubSearchRequest;
import com.nangman.hub.domain.entity.Hub;
import com.nangman.hub.domain.entity.QHub;
import com.nangman.hub.domain.repository.HubRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class HubService {

    private final HubRepository hubRepository;

    public HubResponse createHub(HubPostRequest hubPostRequest) {
        Hub hub = hubRepository.save(hubPostRequest.toEntity());
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
        return hubRepository.findAll(qBuilder, pageable).map(HubResponse::from);
    }

    @Transactional(readOnly = true)
    public HubResponse getHubById(UUID hubId) {
        return HubResponse.from(hubRepository.findHub(hubId));
    }

    public HubResponse updateHub(UUID hubId, HubPostRequest postRequest) {
        Hub hub = hubRepository.findHub(hubId);
        hub.update(
                postRequest.name(),
                postRequest.address(),
                postRequest.latitude(),
                postRequest.longitude(),
                postRequest.managerId()
        );
        return HubResponse.from(hub);
    }

    public void deleteHub(UUID hubId, UUID userId) {
        Hub hub = hubRepository.findHub(hubId);
        hub.delete(userId);
    }
}
