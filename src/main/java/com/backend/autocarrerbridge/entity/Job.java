package com.backend.autocarrerbridge.entity;

import java.time.LocalDate;

import com.backend.autocarrerbridge.util.enums.SalaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.backend.autocarrerbridge.util.enums.State;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "job")
public class Job extends AbstractAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(name = "level")
    private String level;

    @Column(name = "from_salary")
    private Integer fromSalary;

    @Column(name = "to_salary")
    private Integer toSalary;


    @Column(name = "salary_type")
    @Enumerated(EnumType.STRING)  // Chỉ định kiểu enum
    private SalaryType salaryType;

    @Column(name = "job_description", columnDefinition = "TEXT")
    private String jobDescription;

    @Column(name = "requirement", columnDefinition = "TEXT")
    private String requirement;

    @Column(name = "benefit", columnDefinition = "TEXT")
    private String benefit;

    @Column(name = "working_time")
    private String workingTime;

    @Column(name = "status_browse")
    private State statusBrowse;

    @Column(name = "rank_of_job")
    private String rankOfJob;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "work_form")
    private String workForm;

    @Column(name = "gender")
    private String gender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;
}
