package com.backend.autocarrerbridge.dto.response.job;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.backend.autocarrerbridge.entity.Job;
import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.util.enums.SalaryType;
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
public class JobResponse {
    Integer jobId;
    String title;
    LocalDate expireDate;
    String level;
    Integer fromSalary;
    Integer toSalary;
    String rank;
    Integer quantity;
    String workForm;
    SalaryType salaryType;
    String gender;
    String workingTime;
    State statusBrowse;
    Status status;
    LocalDateTime createAt;
    String createBy;
    LocalDateTime updateAt;
    String updateBy;


    public JobResponse(Job job) {
        this.jobId = job.getId();
        this.title = job.getTitle();
        this.expireDate = job.getExpireDate();
        this.level = job.getLevel();
        this.fromSalary = job.getFromSalary();
        this.toSalary = job.getToSalary();
        this.rank = job.getRankOfJob();
        this.quantity = job.getQuantity();
        this.workForm = job.getWorkForm();
        this.salaryType = job.getSalaryType();
        this.gender = job.getGender();
        this.workingTime = job.getWorkingTime();
        this.statusBrowse = job.getStatusBrowse();
        this.status = job.getStatus();
        this.createAt = job.getCreatedAt();
        this.createBy = job.getCreatedBy();
        this.updateAt = job.getUpdatedAt();
        this.updateBy = job.getUpdatedBy();
    }
}
