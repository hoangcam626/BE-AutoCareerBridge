package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "businessuniversity")
public class Businessuniversity {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "status_connected")
    private Integer statusConnected;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Businessid", nullable = false)
    private Business businessid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Universityid", nullable = false)
    private University universityid;

}