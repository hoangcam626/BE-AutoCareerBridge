package com.backend.autocarrerbridge.service;

import java.text.ParseException;

import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import org.springframework.data.domain.Pageable;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.job.JobApprovedRequest;
import com.backend.autocarrerbridge.dto.request.job.JobRejectedRequest;
import com.backend.autocarrerbridge.dto.request.job.JobRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.job.JobApprovedResponse;
import com.backend.autocarrerbridge.dto.response.job.JobRejectedResponse;
import com.backend.autocarrerbridge.dto.response.job.JobResponse;

public interface JobService {
    ApiResponse<Object> getAllJob(String keyword, Pageable pageable);

    ApiResponse<Object> getAllJobOfBusinessPaging(int page, int size, String keyword, Pageable pageable) throws ParseException;

    ApiResponse<Object> createJob(JobRequest jobRequest) throws ParseException;

    ApiResponse<Object> getJobDetail(Integer jobId) throws ParseException;

    ApiResponse<Object> updateJob(Integer jobId, JobRequest jobRequest) throws ParseException;

    ApiResponse<Object> inactiveJob(Integer jobId) throws ParseException;

    JobApprovedResponse approved(JobApprovedRequest req) throws ParseException;

    JobRejectedResponse rejected(JobRejectedRequest req) throws ParseException;

    PagingResponse<JobResponse> getPagingByState(PageInfo info, Integer state);

    ApiResponse<Object> checkDeletePermission(Integer jobId) throws ParseException;
}
