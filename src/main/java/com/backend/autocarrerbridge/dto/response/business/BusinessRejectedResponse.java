package com.backend.autocarrerbridge.dto.response.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class BusinessRejectedResponse {
    private Boolean success;
}
