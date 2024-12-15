package com.nangman.order.application.service;

import com.nangman.order.application.dto.CompanyDto;
import com.nangman.order.application.dto.OrderEvent;
import com.nangman.order.application.dto.request.OrderPostRequest;
import com.nangman.order.application.dto.request.OrderPutRequest;
import com.nangman.order.application.dto.response.OrderGetResponse;
import com.nangman.order.application.dto.response.OrderPostResponse;
import com.nangman.order.common.feign.CompanyClient;
import com.nangman.order.common.util.AuthorizationUtils;
import com.nangman.order.common.util.KafkaProducer;
import com.nangman.order.domain.entity.Order;
import com.nangman.order.domain.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CompanyClient companyClient;
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

    @Transactional(readOnly = true)
    public OrderGetResponse getOrder(UUID orderId) {
        Order order = orderRepository.getById(orderId);
        CompanyDto companyDto = companyClient.getCompanyById(order.getReceiverId());
        authorizationUtils.validateHubManager(order.getReceiverId());
        // TODO: Delivery 생성 후 SHIPPER 권한 확인 테스트 필요
        // authorizationUtils.validateDeliveryShipper(order.getDeliveryId());
        authorizationUtils.validateCompanyAgent(companyDto.agentId());

        return OrderGetResponse.from(order);
    }

    @Transactional
    public OrderGetResponse modifyOrder(UUID orderId, OrderPutRequest request) {
        Order order = orderRepository.getById(orderId);
        authorizationUtils.validateHubManager(order.getReceiverId());

        // 존재하는 company인지 검증
        companyClient.getCompanyById(request.supplierId());
        companyClient.getCompanyById(request.receiverId());

        order.updateAll(
                request.supplierId(),
                request.receiverId(),
                request.productId(),
                request.deliveryId(),
                request.productQuantity(),
                request.requestMessage());

        return OrderGetResponse.from(order);
    }

    @Transactional
    public void deleteOrder(UUID orderId) {
        Order order = orderRepository.getById(orderId);
        authorizationUtils.validateHubManager(order.getReceiverId());
        order.updateIsDeleted(authorizationUtils.getUserIdFromAuthentication());
    }
}