package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.accountresponse.UserUniversityDTO;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.service.UniversityService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/university")
public class UniversityController {
    UniversityService universityService;
    @PostMapping("/register")
    public ApiResponse<Object> registerUniversity(@RequestBody @Valid UserUniversityDTO userUniversityDTO){

        return ApiResponse.builder()
                .code(200)
                .message("University registered successfully")
                .data(universityService.registerUniversity(userUniversityDTO))
                .build();
    }
}
