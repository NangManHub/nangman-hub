package com.nangman.slack.common.util;

import com.nangman.slack.application.dto.feign.UserDto;
import com.nangman.slack.application.dto.kafka.TrackResponse;
import com.nangman.slack.infrastructure.HubClient;
import com.nangman.slack.infrastructure.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageServiceUtil {

    private final UserClient userClient;
    private final HubClient hubClient;

    public UserDto getReceiverInfo(UUID receiverId){
        return userClient.getUserById(receiverId);
    }

    public String generateMessage(String shipperName, TrackResponse trackInfo){

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
