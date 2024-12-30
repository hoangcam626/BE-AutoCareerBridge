package com.backend.autocarrerbridge.dto.response.job;

import com.backend.autocarrerbridge.util.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminJobResponse {
    private Integer id;
    private String title;
    private String businessName;
    private LocalDateTime createdAt;
    private String industryName;
    private State statusBrowse;
    private LocalDate expireDate;
}
