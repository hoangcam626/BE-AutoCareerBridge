package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.ApiResponse;

import java.text.ParseException;

public interface BusinessUniversityService {
    ApiResponse<Object> getSentRequest() throws ParseException;
    ApiResponse<Object> sendRequest(Integer universityId) throws ParseException;
    ApiResponse<Object> cancelRequest(Integer universityId) throws ParseException;
}
