package com.nangman.ai.presentation;

import com.nangman.ai.application.dto.request.AIPostRequest;
import com.nangman.ai.application.dto.response.AIPostResponse;
import com.nangman.ai.application.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("ais")
@RequiredArgsConstructor
@RestController
public class AIController {

    private final AIService aiService;

    @PostMapping
    public AIPostResponse createAI(@RequestBody AIPostRequest req) {
        return aiService.createMessage(req);
    }
}
