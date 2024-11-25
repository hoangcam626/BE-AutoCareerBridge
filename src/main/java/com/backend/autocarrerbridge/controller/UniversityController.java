package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.request.account.UserUniversityRequest;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.service.UniversityService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.backend.autocarrerbridge.util.Constant.REGISTER_UNIVERSITY;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/university")
public class UniversityController {
    UniversityService universityService;
    @PostMapping("/register")
    public ApiResponse<Object> registerUniversity(@RequestBody @Valid UserUniversityRequest userUniversityRequest){

        return ApiResponse.builder()
                .code(SUCCESS)
                .message(REGISTER_UNIVERSITY)
                .data(universityService.registerUniversity(userUniversityRequest))
                .build();
    }
}
