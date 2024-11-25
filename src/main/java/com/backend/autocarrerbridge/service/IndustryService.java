package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.industry.IndustryRequest;
import com.backend.autocarrerbridge.dto.response.industry.IndustryResponse;
import com.backend.autocarrerbridge.dto.ApiResponse;

public interface IndustryService {

    ApiResponse<Object> getAllIndustryPaging(int first, int rows, int page, String name, String code);

    ApiResponse<Object> getAllIndustry();

    ApiResponse<IndustryResponse> createIndustry(IndustryRequest industryRequest);

    ApiResponse<IndustryResponse> updateIndustry(IndustryRequest industryRequest);

    ApiResponse<Object> inactiveIndustry(Integer id);
}
