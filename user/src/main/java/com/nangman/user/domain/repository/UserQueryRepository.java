package com.nangman.user.domain.repository;

import com.nangman.user.application.dto.response.UserGetResponse;
import com.nangman.user.domain.entity.User;
import com.nangman.user.domain.entity.UserRole;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.nangman.user.domain.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<UserGetResponse> searchUser(Pageable pageable, List<UserRole> roles){
        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

        List<User> content = queryFactory
                .selectFrom(user)
                .where(
                        userRoleContains(roles)
                )
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(user.count())
                .from(user)
                .where(
                        userRoleContains(roles)
                );

        List<UserGetResponse> dtoList = content.stream()
                .map(User::toResponseDto)
                .toList();

        return PageableExecutionUtils.getPage(dtoList, pageable, countQuery::fetchOne);
    }

    private BooleanExpression userRoleContains(List<UserRole> roles){
        return roles.isEmpty() ? null : user.role.in(roles);
    }

    private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (pageable.getSort() != null) {
            for (Sort.Order sortOrder : pageable.getSort()) {
                com.querydsl.core.types.Order direction = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
                switch (sortOrder.getProperty()) {
                    case "createdAt":
                        orders.add(new OrderSpecifier<>(direction, user.createdAt));
                        break;
                    case "updatedAt":
                        orders.add(new OrderSpecifier<>(direction, user.updatedAt));
                        break;
                    default:
                        break;
                }
            }
        }

        return orders;
    }
}
