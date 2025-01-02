package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.request.universityjob.UniversityJobRequest;
import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.dto.response.universityjob.UniversityJobResponse;
import com.backend.autocarrerbridge.service.UniversityJobService;
import com.backend.autocarrerbridge.util.enums.State;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/university-job")
public class UniversityJobController {
    private final UniversityJobService universityJobService;

    @GetMapping("/get-all-universities")
    public ApiResponse<List<UniversityResponse>> getAllUniversityApplyJob(@RequestParam("jobId") Integer jobId, @RequestParam("state") String state) {
        var res = universityJobService.getUniversityApplyJob(jobId, State.valueOf(state));
        return new ApiResponse<>(res);
    }

    @GetMapping("/get-all-jobs")
    public ApiResponse<PagingResponse<UniversityJobResponse>> getAllJobInUniversity(@RequestParam("universityId") Integer universityId,
                                                                          @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                                          @RequestParam(value = "keyword", required = false) String keyword,
                                                                          @RequestParam("state") String state
                                                                          ){
        var res = universityJobService.getAllJobsInUniversity(universityId, state, PageInfo.of(pageNo, pageSize,keyword));
        return new ApiResponse<>(res);
    }

    @PostMapping("/approved")
    public ApiResponse<UniversityJobResponse> approved(@Valid @RequestBody UniversityJobRequest req) throws ParseException {
        var res = universityJobService.approve(req);
        return new ApiResponse<>(res);
    }

    @PostMapping("rejected")
    public ApiResponse<UniversityJobResponse> rejected(@Valid @RequestBody UniversityJobRequest req) throws ParseException {
        var res = universityJobService.reject(req);
        return new ApiResponse<>(res);
    }

    @PostMapping("/apply-job")
    public ApiResponse<UniversityJobResponse> apply(@Valid @RequestBody UniversityJobRequest req){
        var res = universityJobService.create(req);
        return new ApiResponse<>(res);
    }

    @GetMapping("/get-status")
    public ApiResponse<String> getStatus(@RequestParam("universityId") Integer universityId ,@RequestParam("jobId") Integer jobId){
        var res = universityJobService.getStatus(universityId, jobId);
        return new ApiResponse<>(res);
    }
}
