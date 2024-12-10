package com.nangman.user.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    // 생성시간
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 수정시간
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 삭제시간
    protected LocalDateTime deletedAt;

    // 생성자
    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    // 수정자
    @LastModifiedBy
    private Long updatedBy;

    // 삭제자
    protected Long deletedBy;

}

