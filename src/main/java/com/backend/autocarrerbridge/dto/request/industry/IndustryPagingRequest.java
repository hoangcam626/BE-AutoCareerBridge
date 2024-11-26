package com.backend.autocarrerbridge.dto.request.industry;

import java.util.List;

import com.backend.autocarrerbridge.dto.response.industry.IndustryResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndustryPagingRequest {
    private Long totalRecords;
    private List<IndustryResponse> data;
}
