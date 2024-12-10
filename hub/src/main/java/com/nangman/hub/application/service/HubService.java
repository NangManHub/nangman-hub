package com.nangman.hub.application.service;

import com.nangman.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HubService {

    private final HubRepository hubRepository;
}
