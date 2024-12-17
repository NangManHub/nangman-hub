package com.nangman.order.domain.repository;

import static com.nangman.order.domain.entity.QOrder.order;

import com.nangman.order.application.dto.OrderDto;
import com.nangman.order.domain.entity.Order;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<OrderDto> searchOrder(UUID supplierId, UUID receiverId, UUID productId, Integer productQuantity, String requestMessage, Pageable pageable) {
        List<Order> orderSearchList = queryFactory
                .selectFrom(order)
                .where(eqSupplierId(supplierId),
                        eqReceiverId(receiverId),
                        eqProductId(productId),
                        eqProductQuantity(productQuantity),
                        containsRequestMessage(requestMessage))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<OrderDto> orderDtoList = orderSearchList.stream()
                .map(OrderDto::from)
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(order.count())
                .from(order)
                .where(eqSupplierId(supplierId),
                        eqReceiverId(receiverId),
                        eqProductId(productId),
                        eqProductQuantity(productQuantity),
                        containsRequestMessage(requestMessage));

        return PageableExecutionUtils.getPage(orderDtoList, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqSupplierId(UUID supplierId) {
        return Optional.ofNullable(supplierId)
                .map(order.supplierId::eq)
                .orElse(null);
    }

    private BooleanExpression eqReceiverId(UUID receiverId) {
        return Optional.ofNullable(receiverId)
                .map(order.receiverId::eq)
                .orElse(null);
    }

    private BooleanExpression eqProductId(UUID productId) {
        return Optional.ofNullable(productId)
                .map(order.productId::eq)
                .orElse(null);
    }

    private BooleanExpression eqProductQuantity(Integer productQuantity) {
        return Optional.ofNullable(productQuantity)
                .map(order.productQuantity::eq)
                .orElse(null);
    }

    private BooleanExpression containsRequestMessage(String requestMessage) {
        return Optional.ofNullable(requestMessage)
                .map(order.requestMessage::contains)
                .orElse(null);
    }
}
