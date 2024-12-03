package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.response.cooperation.CooperationUniversityResponse;

import java.text.ParseException;
import java.util.List;

public interface BusinessUniversityService {
    ApiResponse<Object> getSentRequest() throws ParseException;
    ApiResponse<Object> sendRequest(Integer universityId) throws ParseException;
    ApiResponse<Object> cancelRequest(Integer universityId) throws ParseException;
    List<CooperationUniversityResponse> getAllCooperationOfUniversity() throws ParseException;
    List<CooperationUniversityResponse> getAllCooperationOfUniversityPending() throws ParseException;
    List<CooperationUniversityResponse> getAllCooperationOfUniversityApprove() throws ParseException;
    List<CooperationUniversityResponse> getAllCooperationOfUniversityReject() throws ParseException;
    void approveRequetCooperation (Integer idBusinesUniversity);


}