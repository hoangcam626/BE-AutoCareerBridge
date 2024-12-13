package com.backend.autocarrerbridge.dto.response.industry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckIndustryResponse {
    private boolean exists;

    public CheckIndustryResponse(boolean exists) {
        this.exists = exists;
    }
}
