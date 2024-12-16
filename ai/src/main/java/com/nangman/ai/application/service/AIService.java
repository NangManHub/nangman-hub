package com.nangman.ai.application.service;

import com.nangman.ai.application.dto.request.AIPostRequest;
import com.nangman.ai.application.dto.response.AIPostResponse;
import com.nangman.ai.domain.entity.AI;
import com.nangman.ai.domain.repository.AIRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AIService {

    private final AIRepository aiRepository;
    private final GoogleService googleService;

    public AIPostResponse createMessage(AIPostRequest req) {
        String prompt = req.text() + """
                
                아래 예시처럼 메시지를 한개만 생성해줘.
                
                [메시지 예시]
                주문 번호 : 1
                주문자 정보 : 김말숙 / msk@seafood.world
                상품 정보 : 마른 오징어 50박스
                요청 사항 : 12월 12일 3시까지는 보내주세요!
                발송지 : 경기 북부 센터
                경유지 : 대전광역시 센터, 부산광역시 센터
                도착지 : 부산시 사하구 낙동대로 1번길 1 해산물월드
                배송담당자 : 고길동 / kdk@sparta.world
                
                위 내용을 기반으로 도출된 최종 발송 시한은 12월 10일 오전 9시 입니다.
                """;
        String aiResponse = googleService.genAIResponse(prompt);
        AI ai = aiRepository.save(new AI(prompt, aiResponse));
        return AIPostResponse.of(ai.getId(), ai.getPrompt(), ai.getResponse());
    }
}
