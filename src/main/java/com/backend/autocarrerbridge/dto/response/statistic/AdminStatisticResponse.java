package com.backend.autocarrerbridge.dto.response.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminStatisticResponse {
    private Long businessesTotal;
    private Long universitiesTotal;
    private Long jobsTotal;
    private Long workshopsTotal;
    private Long universityBusinessTotal;
    private Long jobUniversitiesTotal;
    private Long workshopBusinessesTotal;
}
