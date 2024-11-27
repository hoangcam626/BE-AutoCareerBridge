package com.backend.autocarrerbridge.dto.request.job;

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
public class JobRequest {
    String title;
    LocalDate expireDate;
    String level;
    Integer salary;
    String jobDescription;
    String requirement;
    String benefit;
    String workingTime;
    Integer industriesID;
}
