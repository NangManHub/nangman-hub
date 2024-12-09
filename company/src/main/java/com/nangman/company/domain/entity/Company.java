package com.nangman.company.domain.entity;

import com.nangman.company.domain.enums.CompanyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_companies")
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID hubId;

    @Column(nullable = false)
    private UUID agentId;

    @Column(length = 20, nullable = false)
    @Size(min = 1, max = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 8, nullable = false)
    private CompanyType type;

    @Column(length = 50, nullable = false)
    @Size(min = 1, max = 50)
    private String address;
}
