package com.backend.autocarrerbridge.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.backend.autocarrerbridge.util.enums.Status;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAudit implements Serializable {

    @Serial
    private static final long serialVersionUID = 3581298718616904225L;

    @Column(name = "status")
    private Status status;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", insertable = false)
    private String updatedBy;

    //    @PrePersist
    //    public void prePersist() {
    //        this.status = Status.ACTIVE;
    //    }
    //
    //    @PreUpdate
    //    public void preUpdate() {
    //        this.status = Status.ACTIVE;
    //    }

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = Status.ACTIVE; // Chỉ gán khi status chưa được gán
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (this.status == null) {
            this.status = Status.ACTIVE; // Chỉ gán khi status chưa được gán
        }
    }
}
