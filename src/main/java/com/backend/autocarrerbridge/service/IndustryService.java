package com.backend.autocarrerbridge.service;

import java.text.ParseException;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.industry.IndustryRequest;
import org.springframework.data.domain.Pageable;

public interface IndustryService {

    ApiResponse<Object> getAllIndustryPaging(int first, int rows, int page, String name, String code);

    ApiResponse<Object> getAllIndustry();

    ApiResponse<Object> createIndustry(IndustryRequest industryRequest) throws ParseException;

    ApiResponse<Object> updateIndustry(IndustryRequest industryRequest) throws ParseException;

    ApiResponse<Object> inactiveIndustry(Integer id) throws ParseException;

    ApiResponse<Object> createIndustryToBusiness(Integer industryId) throws ParseException;

    ApiResponse<Object> getIndustryOfBusiness(int page, int size, Pageable pageable) throws ParseException;

    ApiResponse<Object> getIndustryDetail(Integer industryId);
}
