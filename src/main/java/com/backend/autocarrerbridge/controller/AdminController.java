package com.backend.autocarrerbridge.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.business.BusinessApprovedRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessRejectedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityApprovedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRejectedRequest;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.UniversityService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * Controller xử lý các API dành riêng cho quản trị viên (admin v sub-admin)
 * để quản lý việc phê duyệt hoặc từ chối tài khoản của doanh nghiệp và trường học.
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUB_ADMIN')")
@SecurityRequirement(name = "AdminAuthorization")
@RequestMapping("/api/admin")
public class AdminController {
    private final BusinessService businessService;
    private final UniversityService universityService;

    /**
     * API phê duyệt tài khoản doanh nghiệp.
     *
     * @param req Yêu cầu chứa thông tin cần thiết để phê duyệt tài khoản doanh nghiệp.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/approved-business")
    public ApiResponse<?> approvedBusiness(BusinessApprovedRequest req) {
        businessService.approvedAccount(req);
        return new ApiResponse<>();
    }

    /**
     * API từ chối tài khoản doanh nghiệp.
     *
     * @param req Yêu cầu chứa lý do và thông tin để từ chối tài khoản doanh nghiệp.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/rejected-business")
    public ApiResponse<?> rejectedBusiness(BusinessRejectedRequest req) {
        businessService.rejectedAccount(req);
        return new ApiResponse<>();
    }

    /**
     * API phê duyệt tài khoản trường đại học.
     *
     * @param req Yêu cầu chứa thông tin cần thiết để phê duyệt tài khoản trường đại học.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/approved-university")
    public ApiResponse<?> approvedUniversity(UniversityApprovedRequest req) {
        universityService.approvedAccount(req);
        return new ApiResponse<>();
    }

    /**
     * API từ chối tài khoản trường đại học.
     *
     * @param req Yêu cầu chứa lý do và thông tin để từ chối tài khoản trường đại học.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/rejected-university")
    public ApiResponse<?> rejectedUniversity(UniversityRejectedRequest req) {
        universityService.rejectedAccount(req);
        return new ApiResponse<>();
    }
}
