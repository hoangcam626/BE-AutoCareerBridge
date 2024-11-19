package com.backend.autocarrerbridge.entity;

import com.backend.autocarrerbridge.util.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAudit implements Serializable {

    @Serial
    private static final long serialVersionUID = 3581298718616904225L;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Integer createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", insertable = false)
    private Integer updatedBy;
//    @PrePersist
//    public void prePersist() {
//        this.status = Status.ACTIVE;
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        this.status = Status.ACTIVE;
//    }
}

