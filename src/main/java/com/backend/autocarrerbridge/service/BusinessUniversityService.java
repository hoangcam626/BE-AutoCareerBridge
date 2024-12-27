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
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;
import org.springframework.data.domain.Pageable;


public interface BusinessUniversityService {
    ApiResponse<Object> getSentRequest(String keyword, State statusConnected, Pageable pageable) throws ParseException;

    ApiResponse<Object> sendRequest(Integer universityId) throws ParseException;

    ApiResponse<Object> cancelRequest(Integer universityId) throws ParseException;

    List<CooperationUniversityResponse> getAllCooperationOfUniversity() throws ParseException;

    List<CooperationUniversityResponse> getAllCooperationOfUniversityPending() throws ParseException;

    List<CooperationUniversityResponse> getAllCooperationOfUniversityApprove() throws ParseException;

    List<CooperationUniversityResponse> getAllCooperationOfUniversityReject() throws ParseException;

    CooperationApproveResponse approveRequestCooperation(CooperationApproveRequest request) throws ParseException;

    CooperationRejectResponse rejectRequestCooperation(CooperationRejectRequest request) throws ParseException;

    long countBussinessUniversity(Integer universityId);

    PagingResponse<CooperationUniversityResponse> gegetAllCooperationOfUniversityPage(String keyword, State statusConnected, Pageable pageable) throws ParseException;

}
