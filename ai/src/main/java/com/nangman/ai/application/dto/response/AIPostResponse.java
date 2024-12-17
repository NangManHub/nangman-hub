package com.nangman.ai.application.dto.response;

import java.util.UUID;

public record AIPostResponse(
        UUID aiId,
        String prompt,
        String response
) {
    public static AIPostResponse of(UUID aiId, String prompt, String response) {
        return new AIPostResponse(aiId, prompt, response);
    }
}
