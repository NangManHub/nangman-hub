package com.nangman.slack.domain.service;

import com.nangman.slack.application.dto.event.DeliveryEvent;
import com.nangman.slack.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackEventListener {

    private final MessageUtil messageUtil;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDeliveryEvent(DeliveryEvent event) {
        messageUtil.sendMessage(event.slackId(), event.message());
    }

}