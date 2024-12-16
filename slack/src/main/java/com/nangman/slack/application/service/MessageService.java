package com.nangman.slack.application.service;

import com.nangman.slack.application.dto.kafka.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    @Transactional
    public void sendDeliveryInfoToShipper(DeliveryResponse deliveryInfo){
        // TODO 서비스 처리
        // 1. 받은 주문 정보 바탕으로 메세지 생성
        // 2. FeignClient로 Shipper의 SlackId 받아옴
        // 3. Slack Message 전달
    }

}
