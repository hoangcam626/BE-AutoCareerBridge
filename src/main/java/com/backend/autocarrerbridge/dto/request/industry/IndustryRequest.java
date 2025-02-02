package com.backend.autocarrerbridge.dto.request.industry;

import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndustryRequest {
    private String name;
    private String code;
    private Status status;
}
