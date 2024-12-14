package com.nangman.order.application.service;

import com.nangman.order.application.dto.OrderEvent;
import com.nangman.order.application.dto.request.OrderPostRequest;
import com.nangman.order.application.dto.response.OrderPostResponse;
import com.nangman.order.common.feign.CompanyClient;
import com.nangman.order.common.util.AuthorizationUtils;
import com.nangman.order.common.util.KafkaProducer;
import com.nangman.order.domain.entity.Order;
import com.nangman.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;
    private final AuthorizationUtils authorizationUtils;

    @Transactional
    public OrderPostResponse createOrder(OrderPostRequest request) {
        authorizationUtils.validateCompanyAgent(request.receiverId());
        authorizationUtils.validateHubManager(request.receiverId());

        // TODO: 상품 수량 확인 로직 추가
        Order order = orderRepository.save(request.toEntity());
        kafkaProducer.sendMessage("order", order.getId().toString(), OrderEvent.from(order));
        return OrderPostResponse.from(order);
    }

}
