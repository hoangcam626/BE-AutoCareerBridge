package com.backend.autocarrerbridge.service;

import java.text.ParseException;


import com.backend.autocarrerbridge.dto.request.industry.DeleteIndustryRequest;
import org.springframework.data.domain.Pageable;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.industry.IndustryRequest;

public interface IndustryService {

    ApiResponse<Object> getAllIndustryPaging(String keyword, Pageable pageable);

    ApiResponse<Object> getAllIndustry();

    ApiResponse<Object> createIndustry(IndustryRequest industryRequest) throws ParseException;

    ApiResponse<Object> updateIndustry(Integer id, IndustryRequest industryRequest) throws ParseException;

    ApiResponse<Object> inactiveIndustry(Integer id) throws ParseException;

    ApiResponse<Object> createIndustryToBusiness(Integer industryId) throws ParseException;

    ApiResponse<Object> getIndustryOfBusiness(String keyword, Pageable pageable) throws ParseException;

    ApiResponse<Object> getIndustryOfBusinessNoPag() throws ParseException;

    ApiResponse<Object> getIndustryDetailOfBusiness(Integer industryId) throws ParseException;

    ApiResponse<Object> getIndustryDetail(Integer industryId) throws ParseException;

    ApiResponse<Object> inactiveIndustryOfBusiness(DeleteIndustryRequest deleteIndustryRequest) throws ParseException;

    ApiResponse<Object> checkIndustryExist(Integer industryId) throws ParseException;

    ApiResponse<Object> getMostUsedIndustry() throws ParseException;

    ApiResponse<Object> getAverageSalaryByIndustry() throws ParseException;

}
