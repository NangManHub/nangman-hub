package com.nangman.slack.application.dto.event;

public record DeliveryEvent(
        String slackId,
        String message
)
{}
