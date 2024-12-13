package com.backend.autocarrerbridge.dto.request.industry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteIndustryRequest {
    List<Integer> businessIndustryId;
}
