package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.industry.IndustryRequest;
import com.backend.autocarrerbridge.dto.ApiResponse;

import java.text.ParseException;

public interface IndustryService {

    ApiResponse<Object> getAllIndustryPaging(int first, int rows, int page, String name, String code);

    ApiResponse<Object> getAllIndustry();

    ApiResponse<Object> createIndustry(IndustryRequest industryRequest) throws ParseException;

    ApiResponse<Object> updateIndustry(IndustryRequest industryRequest) throws ParseException;

    ApiResponse<Object> inactiveIndustry(Integer id) throws ParseException;
}
