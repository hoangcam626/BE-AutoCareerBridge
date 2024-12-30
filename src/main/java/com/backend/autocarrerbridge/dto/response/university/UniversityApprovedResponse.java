package com.backend.autocarrerbridge.dto.response.university;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UniversityApprovedResponse {
    private Boolean success;
}
