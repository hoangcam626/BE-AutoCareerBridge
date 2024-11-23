package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.service.JobService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.backend.autocarrerbridge.dto.AccountRespone.UserBusinessDTO;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.service.BusinessService;

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
    private ApiResponse<?> registerBusiness(@ModelAttribute @Valid UserBusinessDTO userBusinessDTO) {
        return ApiResponse.builder()
                .code(200)
                .message("Business Successfully registered")
                .data(businessService.registerBusiness(userBusinessDTO))
                .build();
    }

    @GetMapping("/get-all-job")
    private ApiResponse<Object> getAllJob() {
        return jobService.getAllJob();
    }
}
