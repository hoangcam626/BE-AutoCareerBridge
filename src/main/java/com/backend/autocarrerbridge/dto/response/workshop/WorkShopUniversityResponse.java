package com.backend.autocarrerbridge.dto.response.workshop;

import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkShopUniversityResponse {
    private UniversityResponse university;
    private List<WorkShopResponse> workshops;
}
