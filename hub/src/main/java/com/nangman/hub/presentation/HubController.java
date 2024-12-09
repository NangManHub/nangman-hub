package com.nangman.hub.presentation;

import com.nangman.hub.application.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("hubs")
@RequiredArgsConstructor
@RestController
public class HubController {

    private final HubService hubService;
}
