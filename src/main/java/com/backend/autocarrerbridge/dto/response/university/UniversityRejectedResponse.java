package com.backend.autocarrerbridge.dto.response.university;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UniversityRejectedResponse {
    private Boolean success;
}
