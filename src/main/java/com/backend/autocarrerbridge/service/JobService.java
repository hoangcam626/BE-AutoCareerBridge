package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.job.JobRequest;

import java.text.ParseException;

public interface JobService {
    ApiResponse<Object> getAllJob() throws ParseException;

    ApiResponse<Object> createJob(JobRequest jobRequest) throws ParseException;

    ApiResponse<Object> getJobDetail(Integer jobId) throws ParseException;

    ApiResponse<Object> updateJob(Integer jobId, JobRequest jobRequest) throws ParseException;
    ApiResponse<Object> inactiveJob(Integer jobId) throws ParseException;


}
