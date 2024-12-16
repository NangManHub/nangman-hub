package com.nangman.slack.common.util;

import com.nangman.slack.application.dto.feign.UserDto;
import com.nangman.slack.application.dto.kafka.TrackResponse;
import com.nangman.slack.infrastructure.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageServiceUtil {

    private final UserClient userClient;

    public UserDto getReceiverInfo(UUID receiverId){
        return userClient.getUserById(receiverId);
    }

    public String generateMessage(String shipperName, TrackResponse trackInfo){

        String destination = trackInfo.toHubId() == null ? trackInfo.address() : trackInfo.toHubName();

        return String.format(
                "%s 기사님! 오늘의 배송건입니다.\n" +
                        "출발 : %s\n" +
                        "도착 : %s\n" +
                        "예상 시간 : %d분\n" +
                        "예상 거리 : %dkm",
                shipperName,
                trackInfo.fromHubName(),
                destination,
                trackInfo.expectTime(),
                trackInfo.expectDistance()
        );
    }
}
