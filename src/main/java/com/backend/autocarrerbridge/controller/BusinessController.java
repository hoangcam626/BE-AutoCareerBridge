package com.backend.autocarrerbridge.controller;

import static com.backend.autocarrerbridge.util.Constant.REGISTER_BUSINESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.account.UserBusinessRequest;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.JobService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/business")
public class BusinessController {
    BusinessService businessService;
    JobService jobService;

    @PostMapping("/register")
    public ApiResponse<Object> registerBusiness(@ModelAttribute @Valid UserBusinessRequest userBusinessRequest) {
        return ApiResponse.builder()
                .code(SUCCESS)
                .message(REGISTER_BUSINESS)
                .data(businessService.registerBusiness(userBusinessRequest))
                .build();
    }

    /**
     * API để lấy danh sách công việc đã đăng tuyển
     * @apiNote để truy vấn danh sách công việc trong cơ sở dữ liệu
     */
    @GetMapping("/get-all-job")
    private ApiResponse<Object> getAllJob() {
        return jobService.getAllJob();
    }
}
