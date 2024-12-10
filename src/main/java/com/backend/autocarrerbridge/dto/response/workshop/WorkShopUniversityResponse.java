package com.backend.autocarrerbridge.dto.response.workshop;

import java.util.List;

import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkShopUniversityResponse {
    private UniversityResponse university;
    private List<WorkShopResponse> workshops;
    private Integer totalRecords;
}
