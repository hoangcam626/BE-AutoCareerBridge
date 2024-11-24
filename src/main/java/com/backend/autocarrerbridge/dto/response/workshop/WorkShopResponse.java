package com.backend.autocarrerbridge.dto.response.workshop;

import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkShopResponse {
    private Integer id;
    private String title;
    private String description;
    private Status status;
    private State statusBrowse;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDate expireDate;

    private Integer workshopImageId;
    private Integer locationId;
    private UniversityResponse university;
}
