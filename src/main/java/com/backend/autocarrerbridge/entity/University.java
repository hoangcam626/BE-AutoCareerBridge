package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "university")
public class University {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "logo_image_id")
    private Integer logoImageId;

    @Column(name = "name")
    private String name;

    @Column(name = "website")
    private String website;

    @Column(name = "founded_year")
    private Integer foundedYear;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserAccountid", nullable = false)
    private Useraccount userAccountid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Locationid", nullable = false)
    private Location locationid;

}