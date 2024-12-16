package com.nangman.slack.application.service;

import com.nangman.slack.application.dto.event.AIEvent;
import com.nangman.slack.application.dto.feign.UserDto;
import com.nangman.slack.application.dto.event.DeliveryEvent;
import com.nangman.slack.application.dto.kafka.AIMessage;
import com.nangman.slack.application.dto.kafka.DeliveryResponse;
import com.nangman.slack.application.dto.kafka.TrackResponse;
import com.nangman.slack.common.util.MessageServiceUtil;
import com.nangman.slack.domain.entity.Slack;
import com.nangman.slack.domain.repository.SlackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final SlackRepository slackRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final MessageServiceUtil messageServiceUtil;

    @Transactional
    public void sendDeliveryInfoToShipper(DeliveryResponse deliveryInfo){

        List<Slack> slackList = new ArrayList<>();

        for(TrackResponse track : deliveryInfo.tracks()){
            // 1. FeignClient로 Shipper의 SlackId 받아옴
            UserDto shipperInfo = messageServiceUtil.getReceiverInfo(track.shipperId());

            // 2. 받은 주문 정보 바탕으로 메세지 생성
            String message = messageServiceUtil.generateMessage(shipperInfo.name(), track);

            // 3. Slack Message 전달
            eventPublisher.publishEvent(new DeliveryEvent(shipperInfo.slackId(), message));

            slackList.add(Slack.builder()
                            .userId(shipperInfo.id())
                            .message(message)
                            .sendAt(LocalDateTime.now())
                            .receiverId(shipperInfo.slackId())
                    .build());
        }

        slackRepository.saveAll(slackList);
    }

    @Transactional
    public void sendOrderInfoToManger(AIMessage aiMessage) {

        UserDto receiverInfo = messageServiceUtil.getReceiverInfo(aiMessage.receiverId());
        String slackId = receiverInfo.slackId();
        String message = aiMessage.message();

        slackRepository.save(Slack.builder()
                .userId(receiverInfo.id())
                .message(message)
                .sendAt(LocalDateTime.now())
                .receiverId(slackId)
                .build());

        eventPublisher.publishEvent(new AIEvent(slackId, message));
    }
}
