package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.request.universityjob.UniversityJobRequest;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.dto.response.universityjob.UniversityJobResponse;
import com.backend.autocarrerbridge.util.enums.State;

import java.text.ParseException;
import java.util.List;

public interface UniversityJobService {
    UniversityJobResponse findById(Integer id);
//    List<UniversityJobResponse> findAll();
//    UniversityJobResponse detail(Integer id);
    UniversityJobResponse create(UniversityJobRequest res);
    UniversityJobResponse approve(UniversityJobRequest res) throws ParseException;
    UniversityJobResponse reject(UniversityJobRequest res) throws ParseException;
    List<UniversityResponse> getUniversityApplyJob(Integer jobId, State state);
    PagingResponse<UniversityJobResponse> getAllJobsInUniversity(Integer universityId, String state, PageInfo req);
    String getStatus(Integer universityId, Integer jobId);
}
