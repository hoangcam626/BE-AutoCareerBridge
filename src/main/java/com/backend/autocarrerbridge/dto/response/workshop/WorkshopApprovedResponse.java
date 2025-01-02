package com.backend.autocarrerbridge.dto.response.workshop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class WorkshopApprovedResponse {
    private Boolean success;
}
