package com.backend.autocarrerbridge.controller;

import static com.backend.autocarrerbridge.util.Constant.REGISTER_UNIVERSITY;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS_MESSAGE;

import jakarta.validation.Valid;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.account.UserUniversityRequest;
import com.backend.autocarrerbridge.service.UniversityService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/university")
public class UniversityController {
    UniversityService universityService;

    @PostMapping("/register")
    public ApiResponse<Object> registerUniversity(@RequestBody @Valid UserUniversityRequest userUniversityRequest) {

        return ApiResponse.builder()
                .code(SUCCESS)
                .message(SUCCESS_MESSAGE)
                .data(universityService.registerUniversity(userUniversityRequest))
                .build();
    }
    @GetMapping
    public ApiResponse<Object> findUniversityByLocationOrName(@RequestParam(required = false) String address,@RequestParam(required = false) String universityName) {
        return ApiResponse.builder().code(SUCCESS).message(SUCCESS_MESSAGE).data(universityService.findUniversityByNameOrLocation(address,universityName)).build();
    }
}
