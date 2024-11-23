package com.backend.autocarrerbridge.dto.job;

import com.backend.autocarrerbridge.entity.Industry;
import com.backend.autocarrerbridge.util.enums.State;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobSdi {
    Integer userId;
    String title;
    String expireDate;
    String level;
    Integer salary;
    String jobDescription;
    String requirement;
    String benefit;
    String workingTime;
    Industry industry;
}
