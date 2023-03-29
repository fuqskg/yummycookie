package com.cookie.yummy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {

    @CreationTimestamp //생성됐을때 시간정보
    @Column(updatable = false) // 수정시에 관여x
    private LocalDateTime createdTime;

    @UpdateTimestamp //업데이트 됐을때 시간정보
    @Column(insertable = false) // 입력시에 관여x
    private LocalDateTime updateTime;

}
