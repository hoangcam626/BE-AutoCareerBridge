package com.backend.autocarrerbridge.dto.response.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateStatisticResponse {
    private Long jobsNo;
    private Long workshopsNo;
    private Long jobUniversitiesNo;
    private Long workshopsBusinessesNo;
    private Long universityBusinessNo;
}
