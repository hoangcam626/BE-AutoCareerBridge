package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.business.BusinessApprovedRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessRejectedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityApprovedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRejectedRequest;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUB_ADMIN')")
@RequestMapping("/api/admin")
public class AdminController {
    private final BusinessService businessService;
    private final UniversityService universityService;

    @PostMapping("/approved-business")
    public ApiResponse<?> approvedBusiness(BusinessApprovedRequest req){
        businessService.approvedAccount(req);
        return new ApiResponse<>();
    }

    @PostMapping("/rejected-business")
    public ApiResponse<?> rejectedBusiness(BusinessRejectedRequest req){
        businessService.rejectedAccount(req);
        return new ApiResponse<>();
    }

    @PostMapping("/approved-university")
    public ApiResponse<?> approvedUniversity(UniversityApprovedRequest req){
        universityService.approvedAccount(req);
        return new ApiResponse<>();
    }

    @PostMapping("/rejected-university")
    public ApiResponse<?> rejectedUniversity(UniversityRejectedRequest req){
        universityService.rejectedAccount(req);
        return new ApiResponse<>();
    }
}
