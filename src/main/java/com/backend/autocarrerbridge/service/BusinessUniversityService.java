package com.backend.autocarrerbridge.service;

import java.text.ParseException;
import java.util.List;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.cooperation.CooperationApproveRequest;
import com.backend.autocarrerbridge.dto.request.cooperation.CooperationRejectRequest;
import com.backend.autocarrerbridge.dto.response.cooperation.CooperationApproveResponse;
import com.backend.autocarrerbridge.dto.response.cooperation.CooperationRejectResponse;
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

    CooperationApproveResponse approveRequestCooperation(CooperationApproveRequest request) throws ParseException;
    CooperationRejectResponse rejectRequestCooperation(CooperationRejectRequest request) throws ParseException;


    PagingResponse<CooperationUniversityResponse> gegetAllCooperationOfUniversityPage(String keyword, Pageable pageable) throws ParseException;

    PagingResponse<CooperationUniversityResponse> getAllCooperationOfUniversityPendingPage( String keyword, Pageable pageable) throws ParseException;

    PagingResponse<CooperationUniversityResponse> getAllCooperationOfUniversityApprovePage( String keyword, Pageable pageable) throws ParseException;

    PagingResponse<CooperationUniversityResponse> getAllCooperationOfUniversityRejectPage( String keyword, Pageable pageable) throws ParseException;
}
