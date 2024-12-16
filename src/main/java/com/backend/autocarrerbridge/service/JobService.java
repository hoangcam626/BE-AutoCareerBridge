package com.backend.autocarrerbridge.service;

import java.text.ParseException;
import java.util.List;

import com.backend.autocarrerbridge.dto.response.industry.JobIndustryResponse;
import com.backend.autocarrerbridge.dto.response.job.BusinessJobResponse;
import com.backend.autocarrerbridge.dto.response.job.BusinessTotalResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import org.springframework.data.domain.Page;
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
    ApiResponse<Object> getAllJob(int page, int size, String keyword, Pageable pageable) throws ParseException;

    ApiResponse<Object> getAllJobOfBusinessPaging(int page, int size, String keyword, Pageable pageable) throws ParseException;

    ApiResponse<Object> createJob(JobRequest jobRequest) throws ParseException;

    ApiResponse<Object> getJobDetail(Integer jobId) throws ParseException;

    ApiResponse<Object> updateJob(Integer jobId, JobRequest jobRequest) throws ParseException;

    ApiResponse<Object> inactiveJob(Integer jobId) throws ParseException;

    JobApprovedResponse approved(JobApprovedRequest req) throws ParseException;

    JobRejectedResponse rejected(JobRejectedRequest req) throws ParseException;

    Page<JobResponse> getPagingByState(PageInfo info, Integer state);

    List<BusinessTotalResponse> getBusinessJob(Pageable pageable);

    PagingResponse<BusinessJobResponse> getJobBusinessByRegion(Pageable pageable, Integer regionId);

    PagingResponse<BusinessJobResponse> getJobBusinessByProvince(Pageable pageable,Integer provinceId);

    PagingResponse<BusinessJobResponse> getJobBusinessByDistrict(Pageable pageable, Integer districtId);

    PagingResponse<BusinessJobResponse> getAllJobBusiness(Pageable pageable);

    PagingResponse<BusinessJobResponse> getAllJobBusinessBySalary(Pageable pageable,Long minSalary,Long maxSalary);

    PagingResponse<BusinessJobResponse> getAllJobBusinessByIndustry(Pageable pageable,Integer industryId);


    List<JobIndustryResponse> getTotalJobByIndustry(Pageable pageable);
    PagingResponse<JobResponse> getPagingByState(PageInfo info, Integer state);

    ApiResponse<Object> checkDeletePermission(Integer jobId) throws ParseException;
}
