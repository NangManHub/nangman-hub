package com.nangman.order.application.service;

import com.nangman.order.application.dto.CompanyDto;
import com.nangman.order.application.dto.HubDto;
import com.nangman.order.application.dto.OrderEvent;
import com.nangman.order.application.dto.request.OrderPostRequest;
import com.nangman.order.application.dto.response.OrderPostResponse;
import com.nangman.order.common.feign.CompanyClient;
import com.nangman.order.common.feign.HubClient;
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
    private final CompanyClient companyClient;
    private final HubClient hubClient;
    private final KafkaProducer kafkaProducer;
    private final AuthorizationUtils authorizationUtils;

    @Transactional
    public OrderPostResponse createOrder(OrderPostRequest request) {
        authorizationUtils.validateCompanyAgent(request.receiverId());
        authorizationUtils.validateHubManager(request.receiverId());

        // TODO: 추후 메시징 기반 보상 트랜잭션 방식으로 리팩토링
        companyClient.checkProductQuantity(request.productId(), request.productQuantity());

        Order order = orderRepository.save(request.toEntity());
        CompanyDto fromCompany = companyClient.getCompanyById(request.supplierId());
        CompanyDto toCompany = companyClient.getCompanyById(request.receiverId());

        kafkaProducer.sendMessage("order.create-success", order.getId().toString(), OrderEvent.from(order, fromCompany, toCompany));

        return OrderPostResponse.from(order);
    }

}
