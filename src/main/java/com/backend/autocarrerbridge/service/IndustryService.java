package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.industry.IndustrySdi;
import com.backend.autocarrerbridge.dto.industry.IndustrySdo;
import com.backend.autocarrerbridge.dto.industry.IndustryUpdateSdi;
import com.backend.autocarrerbridge.model.api.ApiResponse;

public interface IndustryService {

    ApiResponse<Object> getAllIndustryPaging(int first, int rows, int page, String name, String code);
    ApiResponse<Object> getAllIndustry();
    ApiResponse<IndustrySdo> createIndustry(IndustrySdi industrySdi);
    ApiResponse<IndustrySdo> updateIndustry(IndustryUpdateSdi industryUpdateSdi);
    ApiResponse<Object> inactiveIndustry(Integer id);
}
