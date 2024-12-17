package com.nangman.ai.application.service;

import com.nangman.ai.application.dto.kafka.AIEvent;
import com.nangman.ai.application.dto.kafka.AIMessage;
import com.nangman.ai.application.dto.request.AIPostRequest;
import com.nangman.ai.application.dto.response.AIPostResponse;
import com.nangman.ai.application.dto.response.HubResponse;
import com.nangman.ai.application.dto.response.OrderResponse;
import com.nangman.ai.application.dto.response.UserResponse;
import com.nangman.ai.domain.entity.AI;
import com.nangman.ai.domain.repository.AIRepository;
import com.nangman.ai.infrastructure.HubClient;
import com.nangman.ai.infrastructure.OrderClient;
import com.nangman.ai.infrastructure.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AIService {

    private final AIRepository aiRepository;
    private final GoogleService googleService;
    private final OrderClient orderClient;
    private final UserClient userClient;
    private final HubClient hubClient;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public AIPostResponse createMessage(AIPostRequest req) {
        // get order
        OrderResponse orderRes = orderClient.getOrder(req.orderId());
        // get user
        UserResponse recipientRes = userClient.getUser(req.recipient());
        UserResponse shipperRes = userClient.getUser(req.shipperId());
        // get hub
        HubResponse hubRes = hubClient.getHub(req.fromHubId());

        String prompt = genPrompt(req, orderRes, recipientRes, shipperRes, hubRes);
        String aiResponse = googleService.genAIResponse(prompt);
        AI ai = aiRepository.save(new AI(prompt, aiResponse));

        eventPublisher.publishEvent(new AIEvent(ai.getId(), new AIMessage(hubRes.managerId(), aiResponse)));

        return AIPostResponse.of(ai.getId(), ai.getPrompt(), ai.getResponse());
    }

    private String genPrompt(AIPostRequest req,
                             OrderResponse orderRes,
                             UserResponse recipientRes,
                             UserResponse shipperRes,
                             HubResponse hubRes) {
        return """
                1. 메시지 형식은 다음과 같아.
                주문 번호
                주문자 정보
                상품 정보
                요청 사항
                발송지
                경유지
                도착지
                배송담당자
                
                최종 발송 시한 안내
                
                2. 메시지 예시는 다음과 같아.
                주문 번호 : 1
                주문자 정보 : 김말숙 / msk@seafood.world
                상품 정보 : 마른 오징어 50박스
                요청 사항 : 12월 12일 3시까지는 보내주세요!
                발송지 : 경기 북부 센터
                경유지 : 대전광역시 센터, 부산광역시 센터
                도착지 : 부산시 사하구 낙동대로 1번길 1 해산물월드
                배송담당자 : 고길동 / kdk@sparta.world
                
                위 내용을 기반으로 도출된 최종 발송 시한은 12월 10일 오전 9시 입니다.
                
                3. 정보는 다음과 같아.
                주문 번호 : %s
                주문자 정보 : %s / %s
                상품 정보 : %s, %d
                요청 사항 : %s
                발송지 : %s
                경유지 : %s
                도착지 : %s
                배송담당자 : %s / %s
                
                경유시간(분) : %d
                
                4. (3) 정보의 요청 사항에 마감 시각이 있으면 그 시각까지, 없으면 현재 시각에서 3일 후를 도착 시각으로, (3) 정보의 경유시간을 고려해 최종 발송 시한을 계산하고 (2) 메시지 예시와 같이 1개만 생성해줘.
                """
                .formatted(
                        orderRes.orderId(),
                        recipientRes.name(), recipientRes.slackId(),
                        orderRes.productName(), orderRes.productQuantity(),
                        orderRes.requestMessage() == null ? "없음" : orderRes.requestMessage(),
                        hubRes.name(),
                        String.join(", ", req.toHubNameList()),
                        req.address(),
                        shipperRes.name(), shipperRes.slackId(),
                        req.toHubDurationList().stream().mapToInt(Integer::intValue).sum()
                );
    }
}
