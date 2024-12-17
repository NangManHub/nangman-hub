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

    public String generateMessage(String shipperName, TrackResponse trackInfo, boolean isLast) {

        String template = isLast
                ?
                """
                %s 기사님! 오늘의 업체배송건입니다.
                도착 : %s
                """
                :
                """
                %s 기사님! 오늘의 허브배송건입니다.
                출발 : %s
                도착 : %s
                예상 시간 : %d분
                예상 거리 : %dkm
                """;

        return isLast
                ? template.formatted(
                shipperName,
                trackInfo.address() == null ? "알 수 없음" : trackInfo.address()
        )
                : template.formatted(
                shipperName,
                trackInfo.fromHubName() == null ? "알 수 없음" : trackInfo.fromHubName(),
                trackInfo.toHubName() == null ? "알 수 없음" : trackInfo.toHubName(),
                trackInfo.expectTime(),
                trackInfo.expectDistance()
        );
    }

}
