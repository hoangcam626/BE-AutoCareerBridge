package com.backend.autocarrerbridge.dto.response.job;

import com.backend.autocarrerbridge.entity.Employee;
import com.backend.autocarrerbridge.entity.Industry;
import com.backend.autocarrerbridge.entity.Job;
import com.backend.autocarrerbridge.util.enums.State;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobResponse {
    Integer userId;
    String title;
    LocalDate expireDate;
    String level;
    Integer salary;
    String jobDescription;
    String requirement;
    String benefit;
    String workingTime;
    State statusBrowse;
    Industry industry;
    Employee employee;

    public JobResponse(Job job) {
        this.userId = job.getBusiness().getId();
        this.title = job.getTitle();
        this.expireDate = job.getExpireDate();
        this.level = job.getLevel();
        this.salary = job.getSalary();
        this.jobDescription = job.getJobDescription();
        this.requirement = job.getRequirement();
        this.benefit = job.getBenefit();
        this.workingTime = job.getWorkingTime();
        this.statusBrowse = job.getStatusBrowse();
        this.industry = job.getIndustry();
        this.employee = job.getEmployee();
    }

}
