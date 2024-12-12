package com.nangman.company.domain.repository;

import static com.nangman.company.domain.entity.QProduct.product;

import com.nangman.company.application.dto.ProductDto;
import com.nangman.company.domain.entity.Product;
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
public class ProductQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Page<ProductDto> searchProduct(String name, UUID hubId, UUID companyId, Integer quantity, Pageable pageable) {
        List<Product> productSearchList = queryFactory
                .selectFrom(product)
                .where(eqProductName(name),
                        eqProductHubId(hubId),
                        eqProductCompanyId(companyId),
                        goeProductQuantity(quantity),
                        isNotDeleted())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ProductDto> productDtoList = productSearchList.stream()
                .map(ProductDto::from)
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count())
                .from(product)
                .where(eqProductName(name),
                        eqProductHubId(hubId),
                        eqProductCompanyId(companyId),
                        goeProductQuantity(quantity),
                        isNotDeleted()
                );

        return PageableExecutionUtils.getPage(productDtoList, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqProductName(String name) {
        return Optional.ofNullable(name)
                .map(product.name::contains)
                .orElse(null);
    }

    private BooleanExpression eqProductHubId(UUID hubId) {
        return Optional.ofNullable(hubId)
                .map(product.hubId::eq)
                .orElse(null);
    }

    private BooleanExpression eqProductCompanyId(UUID companyId) {
        return Optional.ofNullable(companyId)
                .map(product.companyId::eq)
                .orElse(null);
    }

    // request quantity 이상인 항목만 불러오는 조건
    private BooleanExpression goeProductQuantity(Integer quantity) {
        return Optional.ofNullable(quantity)
                .map(product.quantity::goe)
                .orElse(null);
    }

    private BooleanExpression isNotDeleted() {
        return product.isDelete.isFalse();
    }

}
