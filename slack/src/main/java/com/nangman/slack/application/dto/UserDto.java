package com.nangman.slack.application.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String name,
        String slackId
) {
}
