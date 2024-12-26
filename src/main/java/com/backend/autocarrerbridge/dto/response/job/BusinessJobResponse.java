package com.backend.autocarrerbridge.dto.response.job;

import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.backend.autocarrerbridge.util.Constant.POSTFIX_DATE;
import static com.backend.autocarrerbridge.util.Constant.PREFIX_DATE;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessJobResponse {
    String businessName;
    Integer jobId;
    String title;
    String expireDate;
    String level;
    Integer toSalary;
    Integer fromSalary;
    String description;
    String workingTime;
    String benefit;
    String requirement;
    State statusBrowse;
    Status status;
    Integer imageBusinessId;
    String address;
    String province;
    String district;
    String ward;
    @JsonIgnore
    LocalDateTime createAt;
    @JsonIgnore
    String createBy;
    @JsonIgnore
    LocalDateTime updateAt;
    @JsonIgnore
    String updateBy;

    public BusinessJobResponse(
            String businessName,
            Integer jobId,
            String title,
            LocalDate expireDateInput,
            String level,
            Integer toSalary,
            Integer fromSalary,
            String description,
            String workingTime,
            String requirement,
            String benefit,
            State statusBrowse,
            Status status,
            Integer imageBusinessId,
            String address,
            String province,
            String district,
            String ward,
            LocalDateTime createAt,
            String createBy,
            LocalDateTime updateAt,
            String updateBy
    ) {
        this.businessName = businessName;
        this.jobId = jobId;
        this.title = title;
        this.expireDate = PREFIX_DATE + ChronoUnit.DAYS.between(LocalDate.now(), expireDateInput) + POSTFIX_DATE;
        this.level = level;
        this.fromSalary = fromSalary;
        this.toSalary = toSalary;
        this.workingTime = workingTime;
        this.requirement = requirement;
        this.statusBrowse = statusBrowse;
        this.status = status;
        this.benefit = benefit;
        this.imageBusinessId = imageBusinessId;
        this.address = address;
        this.province = province;
        this.district = district;
        this.description = description;
        this.ward = ward;
        this.createAt = createAt;
        this.createBy = createBy;
        this.updateAt = updateAt;
        this.updateBy = updateBy;
    }
}
