package com.backend.autocarrerbridge.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.backend.autocarrerbridge.dto.AccountRespone.UserUniversityDTO;
import com.backend.autocarrerbridge.model.api.ApiResponse;
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
    private ApiResponse<?> registerUniversity(@RequestBody @Valid UserUniversityDTO userUniversityDTO) {

        return ApiResponse.builder()
                .code(200)
                .message("University registered successfully")
                .data(universityService.registerUniversity(userUniversityDTO))
                .build();
    }
}
