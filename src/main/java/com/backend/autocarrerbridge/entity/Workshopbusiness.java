package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "workshopbusiness")
public class Workshopbusiness {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "status_connected")
    private Integer statusConnected;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_by")
    private Integer updateBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Workshopid", nullable = false)
    private Workshop workshopid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Businessid", nullable = false)
    private Business businessid;

}