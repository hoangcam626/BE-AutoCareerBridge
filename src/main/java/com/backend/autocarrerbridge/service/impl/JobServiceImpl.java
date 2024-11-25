package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.repository.JobRepository;
import com.backend.autocarrerbridge.service.JobService;
import com.backend.autocarrerbridge.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_CODE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public ApiResponse<Object> getAllJob() {
        List<JobResponse> jobs = jobRepository.getAllJob();
        if (jobs.isEmpty()) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, jobs);
    }
}
