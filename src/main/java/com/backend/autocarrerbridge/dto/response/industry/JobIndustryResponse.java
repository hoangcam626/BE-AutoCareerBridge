package com.backend.autocarrerbridge.dto.response.industry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobIndustryResponse {
    private Integer id;
    private String industryName;
    private Long totalJobs;

}
