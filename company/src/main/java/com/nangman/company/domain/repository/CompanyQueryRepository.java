package com.nangman.company.domain.repository;

import com.nangman.company.application.dto.CompanyDto;
import com.nangman.company.domain.entity.Company;
import com.nangman.company.domain.entity.QCompany;
import com.nangman.company.domain.enums.CompanyType;
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
import static com.nangman.company.domain.entity.QCompany.company;

@Repository
@RequiredArgsConstructor
public class CompanyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<CompanyDto> searchCompany(String name, UUID hubId, UUID agentId, CompanyType type, String address, Pageable pageable) {
        List<Company> companySearchList = queryFactory
                .selectFrom(company)
                .where(eqCompanyName(name),
                        eqCompanyHubId(hubId),
                        eqCompanyAgentId(agentId),
                        eqCompanyType(type),
                        eqCompanyAddress(address))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<CompanyDto> companyDtoList = companySearchList.stream()
                .map(CompanyDto::from)
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(company.count())
                .from(company)
                .where(eqCompanyName(name),
                        eqCompanyHubId(hubId),
                        eqCompanyAgentId(agentId),
                        eqCompanyType(type),
                        eqCompanyAddress(address)
                );

        return PageableExecutionUtils.getPage(companyDtoList, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqCompanyName(String name) {
        return Optional.ofNullable(name)
                .map(QCompany.company.name::contains)
                .orElse(null);
    }

    private BooleanExpression eqCompanyHubId(UUID hubId) {
        return Optional.ofNullable(hubId)
                .map(QCompany.company.hubId::eq)
                .orElse(null);
    }

    private BooleanExpression eqCompanyAgentId(UUID agentId) {
        return Optional.ofNullable(agentId)
                .map(QCompany.company.agentId::eq)
                .orElse(null);
    }

    private BooleanExpression eqCompanyType(CompanyType type) {
        return Optional.ofNullable(type)
                .map(QCompany.company.type::eq)
                .orElse(null);
    }

    private BooleanExpression eqCompanyAddress(String address) {
        return Optional.ofNullable(address)
                .map(QCompany.company.address::contains)
                .orElse(null);
    }
}
