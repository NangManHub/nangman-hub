package com.nangman.order.application.service;

import com.nangman.order.application.dto.CompanyDto;
import com.nangman.order.application.dto.OrderDto;
import com.nangman.order.application.dto.ProductDto;
import com.nangman.order.application.dto.event.DeliveryEvent;
import com.nangman.order.application.dto.event.OrderEvent;
import com.nangman.order.application.dto.request.OrderPostRequest;
import com.nangman.order.application.dto.request.OrderPutRequest;
import com.nangman.order.application.dto.response.OrderDetailGetResponse;
import com.nangman.order.application.dto.response.OrderGetResponse;
import com.nangman.order.application.dto.response.OrderPostResponse;
import com.nangman.order.application.dto.response.OrderSearchGetResponse;
import com.nangman.order.common.feign.CompanyClient;
import com.nangman.order.common.util.AuthorizationUtils;
import com.nangman.order.common.util.KafkaProducer;
import com.nangman.order.domain.entity.Order;
import com.nangman.order.domain.repository.OrderQueryRepository;
import com.nangman.order.domain.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
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
        authorizationUtils.validateDeliveryShipper(order.getDeliveryId());
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

    public OrderSearchGetResponse searchOrder(UUID supplierId, UUID receiverId, UUID productId, Integer productQuantity, String requestMessage, Pageable pageable) {
        Page<OrderDto> searchOrderList = orderQueryRepository.searchOrder(supplierId, receiverId, productId, productQuantity, requestMessage, pageable);
        return OrderSearchGetResponse.from(searchOrderList);
    }

    public OrderDetailGetResponse getOrderForAI(UUID orderId) {
        Order order = orderRepository.getById(orderId);
        ProductDto productDto = companyClient.getProductById(order.getProductId());
        return OrderDetailGetResponse.of(order, productDto);
    }

    @KafkaListener(topics = "delivery.create-success", groupId = "order", containerFactory = "kafkaDeliveryEventContainerFactory")
    @Transactional
    public void updateDeliveryId(DeliveryEvent deliveryEvent) {
        Order order = orderRepository.getById(deliveryEvent.orderId());
        order.updateDeliveryId(deliveryEvent.id());
    }

}
