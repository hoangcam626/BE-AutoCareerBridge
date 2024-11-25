package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.request.account.UserBusinessRequest;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.job.JobRequest;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.JobService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

import static com.backend.autocarrerbridge.util.Constant.REGISTER_BUSINESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/business")
public class BusinessController {
    BusinessService businessService;
    JobService jobService;
    @PostMapping("/register")
    public ApiResponse<Object> registerBusiness(@ModelAttribute @Valid UserBusinessRequest userBusinessRequest){
        return ApiResponse.builder()
                .code(SUCCESS)
                .message(REGISTER_BUSINESS)
                .data(businessService
                        .registerBusiness(userBusinessRequest))
                .build();
    }

    /**
     * API để lấy danh sách công việc đã đăng tuyển
     * @apiNote để truy vấn danh sách công việc trong cơ sở dữ liệu
     */
    @GetMapping("/get-all-job")
    private ApiResponse<Object> getAllJob() throws ParseException {
        return jobService.getAllJob();
    }

    /**
     * API để lấy chi tiết công việc đã đăng tuyển
     * @apiNote để truy vấn chi tiết công việc trong cơ sở dữ liệu
     */
    @GetMapping("/get-detail")
    private ApiResponse<Object> getDetail(@RequestParam Integer id) throws ParseException {
        return jobService.getJobDetail(id);
    }

    /**
     * API để tao công việc
     * @apiNote để để thêm công việc vào cơ sở dữ liệu
     */
    @PostMapping("/create-job")
    private ApiResponse<Object> createJob(@RequestBody JobRequest jobRequest) throws ParseException {
        return jobService.createJob(jobRequest);
    }

    /**
     * API để cập nhật công việc
     * @apiNote để để cập nhật thông tin công việc vào cơ sở dữ liệu
     */
    @PutMapping("/update-job")
    private ApiResponse<Object> updateJob(@RequestParam Integer jobId, @RequestBody JobRequest jobRequest) throws ParseException {
        return jobService.updateJob(jobId, jobRequest);
    }

    /**
     * API để vô hiệu hóa công việc
     * @apiNote để để vô hiệu hóa công việc đã đăng
     */
    @PutMapping("/inactive-job")
    private ApiResponse<Object> inactiveJob(@RequestParam Integer jobId) throws ParseException {
        return jobService.inactiveJob(jobId);
    }
}
