package com.backend.autocarrerbridge.service;

import java.text.ParseException;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.job.JobApprovedRequest;
import com.backend.autocarrerbridge.dto.request.job.JobRejectedRequest;
import com.backend.autocarrerbridge.dto.request.job.JobRequest;
import com.backend.autocarrerbridge.dto.response.job.JobApprovedResponse;
import com.backend.autocarrerbridge.dto.response.job.JobRejectedResponse;

public interface JobService {
    ApiResponse<Object> getAllJob() throws ParseException;

    ApiResponse<Object> createJob(JobRequest jobRequest) throws ParseException;

    ApiResponse<Object> getJobDetail(Integer jobId) throws ParseException;

    ApiResponse<Object> updateJob(Integer jobId, JobRequest jobRequest) throws ParseException;

    ApiResponse<Object> inactiveJob(Integer jobId) throws ParseException;

    JobApprovedResponse approved(JobApprovedRequest req);

    JobRejectedResponse rejected(JobRejectedRequest req);
}
