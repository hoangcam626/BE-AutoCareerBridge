package com.backend.autocarrerbridge.dto.response.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class JobApprovedResponse {
    private Boolean success;
}
