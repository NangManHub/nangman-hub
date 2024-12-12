package com.nangman.company.domain.entity;

import com.nangman.company.application.dto.request.CompanyPutRequest;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "p_companies")
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID hubId;

    @Column(unique = true, nullable = false)
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

    public void updateAll(CompanyPutRequest request) {
        this.hubId = request.hubId();
        this.agentId = request.agentId();
        this.name = request.name();
        this.type = request.type();
        this.address = request.address();
    }
}
