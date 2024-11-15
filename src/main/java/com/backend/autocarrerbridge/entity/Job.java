package com.backend.autocarrerbridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "job")
public class Job {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(name = "level")
    private String level;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "requirement")
    private String requirement;

    @Column(name = "benefit")
    private String benefit;

    @Column(name = "working_time")
    private String workingTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "`status_ browse`")
    private Integer statusBrowse;

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
    @JoinColumn(name = "Employeeid", nullable = false)
    private Employee employeeid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Industryid", nullable = false)
    private Industry industryid;

}