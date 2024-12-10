package com.nangman.hub.application.service;

import com.nangman.hub.application.dto.HubPostRequest;
import com.nangman.hub.application.dto.HubResponse;
import com.nangman.hub.common.exception.CustomException;
import com.nangman.hub.common.exception.ExceptionCode;
import com.nangman.hub.domain.entity.Hub;
import com.nangman.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
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
    public HubResponse getHubById(UUID hubId) {
        return HubResponse.from(findHub(hubId));
    }

    public HubResponse updateHub(UUID hubId, HubPostRequest postRequest) {
        Hub hub = findHub(hubId);
        hub.update(postRequest);
        return HubResponse.from(hub);
    }

    public void deleteHub(UUID hubId, UUID userId) {
        Hub hub = findHub(hubId);
        hub.delete(userId);
    }

    private Hub findHub(UUID hubId) {
        return hubRepository.findByIdAndIsDeleteFalse(hubId)
                .orElseThrow(() -> new CustomException(ExceptionCode.HUB_NOT_FOUND));
    }
}
