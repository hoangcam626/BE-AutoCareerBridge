package com.backend.autocarrerbridge.service;

import java.text.ParseException;

import com.backend.autocarrerbridge.dto.ApiResponse;

public interface BusinessUniversityService {
    ApiResponse<Object> getSentRequest() throws ParseException;

    ApiResponse<Object> sendRequest(Integer universityId) throws ParseException;

    ApiResponse<Object> cancelRequest(Integer universityId) throws ParseException;
}
