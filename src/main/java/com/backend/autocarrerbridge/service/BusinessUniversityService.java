package com.backend.autocarrerbridge.service;

import java.text.ParseException;
import java.util.List;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.response.cooperation.CooperationUniversityResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import org.springframework.data.domain.Pageable;


public interface BusinessUniversityService {
    ApiResponse<Object> getSentRequest() throws ParseException;

    ApiResponse<Object> sendRequest(Integer universityId) throws ParseException;

    ApiResponse<Object> cancelRequest(Integer universityId) throws ParseException;

    List<CooperationUniversityResponse> getAllCooperationOfUniversity() throws ParseException;

    List<CooperationUniversityResponse> getAllCooperationOfUniversityPending() throws ParseException;

    List<CooperationUniversityResponse> getAllCooperationOfUniversityApprove() throws ParseException;

    List<CooperationUniversityResponse> getAllCooperationOfUniversityReject() throws ParseException;

    void approveRequestCooperation(Integer idBusinesUniversityApprove);
    void rejectRequestCooperation(Integer idBusinesUniversityReject);


    PagingResponse<CooperationUniversityResponse> gegetAllCooperationOfUniversityPage(String keyword, Pageable pageable) throws ParseException;

    PagingResponse<CooperationUniversityResponse> getAllCooperationOfUniversityPendingPage( String keyword, Pageable pageable) throws ParseException;

    PagingResponse<CooperationUniversityResponse> getAllCooperationOfUniversityApprovePage( String keyword, Pageable pageable) throws ParseException;

    PagingResponse<CooperationUniversityResponse> getAllCooperationOfUniversityRejectPage( String keyword, Pageable pageable) throws ParseException;
}
