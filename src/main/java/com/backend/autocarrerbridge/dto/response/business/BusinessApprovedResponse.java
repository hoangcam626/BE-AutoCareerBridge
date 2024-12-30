package com.backend.autocarrerbridge.dto.response.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class BusinessApprovedResponse {
    private Boolean success;
}
