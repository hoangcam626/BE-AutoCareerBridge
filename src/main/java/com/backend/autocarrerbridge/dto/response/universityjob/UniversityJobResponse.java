package com.backend.autocarrerbridge.dto.response.universityjob;

import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.util.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityJobResponse {
    private UniversityResponse universityResponse;
    private JobResponse jobResponse;
    private State statusConnected;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
