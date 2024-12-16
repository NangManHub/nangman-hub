package com.nangman.slack.application.service;

import com.nangman.slack.application.dto.feign.UserDto;
import com.nangman.slack.application.dto.event.DeliveryEvent;
import com.nangman.slack.application.dto.kafka.DeliveryResponse;
import com.nangman.slack.application.dto.kafka.TrackResponse;
import com.nangman.slack.domain.entity.Slack;
import com.nangman.slack.domain.repository.SlackRepository;
import com.nangman.slack.infrastructure.HubClient;
import com.nangman.slack.infrastructure.UserClient;
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

    private final HubClient hubClient;
    private final UserClient userClient;
    private final SlackRepository slackRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void sendDeliveryInfoToShipper(DeliveryResponse deliveryInfo){

        List<Slack> slackList = new ArrayList<>();

        for(TrackResponse track : deliveryInfo.tracks()){
            // 1. FeignClient로 Shipper의 SlackId 받아옴
            UserDto shipperInfo = userClient.getUserById(track.shipperId());

            // 2. 받은 주문 정보 바탕으로 메세지 생성
            String message = generateMessage(shipperInfo.name(), track);

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

    private String generateMessage(String shipperName, TrackResponse trackInfo){

        String fromHubName = hubClient.getHub(trackInfo.fromHubId()).name();
        String destination = trackInfo.toHubId() == null ? trackInfo.address() : hubClient.getHub(trackInfo.toHubId()).name();

        return String.format(
                "%s 기사님! 오늘의 배송건입니다.\n" +
                        "출발 : %s\n" +
                        "도착 : %s\n" +
                        "예상 시간 : %d분\n" +
                        "예상 거리 : %dkm",
                shipperName,
                fromHubName,
                destination,
                trackInfo.expectTime(),
                trackInfo.expectDistance()
        );
    }

}
