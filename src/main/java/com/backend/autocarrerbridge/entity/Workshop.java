package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "workshop")
public class Workshop {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "workshop_image_id")
    private Integer workshopImageId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "status_browse")
    private Integer statusBrowse;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_by")
    private Integer updateBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Universityid", nullable = false)
    private University universityid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Locationid", nullable = false)
    private Location locationid;

}