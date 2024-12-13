package com.backend.autocarrerbridge.dto.response.job;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.dto.response.employee.EmployeeResponse;
import com.backend.autocarrerbridge.dto.response.industry.IndustryResponse;
import com.backend.autocarrerbridge.entity.Job;
import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.util.enums.State;

import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobDetailResponse {
    Integer jobId;
    String title;
    LocalDate expireDate;
    String level;
    Integer salary;
    String jobDescription;
    String requirement;
    String benefit;
    String workingTime;
    State statusBrowse;
    Status status;
    Location workLocation;
    LocalDateTime createAt;
    String createBy;
    LocalDateTime updateAt;
    String updateBy;
    IndustryResponse industry;
    EmployeeResponse employee;
    BusinessResponse business;

    public JobDetailResponse(Job job, IndustryResponse industry, BusinessResponse business, EmployeeResponse employee) {
        this.jobId = job.getId();
        this.title = job.getTitle();
        this.expireDate = job.getExpireDate();
        this.level = job.getLevel();
        this.salary = job.getSalary();
        this.jobDescription = job.getJobDescription();
        this.requirement = job.getRequirement();
        this.benefit = job.getBenefit();
        this.workingTime = job.getWorkingTime();
        this.statusBrowse = job.getStatusBrowse();
        this.status = job.getStatus();
        this.workLocation = job.getBusiness().getLocation();
        this.createAt = job.getCreatedAt();
        this.createBy = job.getCreatedBy();
        this.updateAt = job.getUpdatedAt();
        this.updateBy = job.getUpdatedBy();
        this.industry = industry;
        this.employee = employee;
        this.business = business;
    }
}
