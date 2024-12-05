package com.backend.autocarrerbridge.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.business.BusinessApprovedRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessRejectedRequest;
import com.backend.autocarrerbridge.dto.request.job.JobApprovedRequest;
import com.backend.autocarrerbridge.dto.request.job.JobRejectedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityApprovedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRejectedRequest;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopApprovedRequest;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopRejectedRequest;
import com.backend.autocarrerbridge.dto.response.business.BusinessApprovedResponse;
import com.backend.autocarrerbridge.dto.response.business.BusinessRejectedResponse;
import com.backend.autocarrerbridge.dto.response.job.JobApprovedResponse;
import com.backend.autocarrerbridge.dto.response.job.JobRejectedResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityApprovedResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityRejectedResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopApprovedResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopRejectedResponse;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.JobService;
import com.backend.autocarrerbridge.service.UniversityService;

import com.backend.autocarrerbridge.service.WorkShopService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import java.text.ParseException;

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
    private final JobService jobService;
    private final WorkShopService workShopService;

    /**
     * API phê duyệt tài khoản doanh nghiệp.
     *
     * @param req Yêu cầu chứa thông tin cần thiết để phê duyệt tài khoản doanh nghiệp.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/approved-business")
    public ApiResponse<BusinessApprovedResponse> approvedBusiness(@Valid @RequestBody BusinessApprovedRequest req){
        var res = businessService.approvedAccount(req);
        return new ApiResponse<>(res);
    }

    /**
     * API từ chối tài khoản doanh nghiệp.
     *
     * @param req Yêu cầu chứa lý do và thông tin để từ chối tài khoản doanh nghiệp.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/rejected-business")
    public ApiResponse<BusinessRejectedResponse> rejectedBusiness(@Valid @RequestBody BusinessRejectedRequest req){
        var res = businessService.rejectedAccount(req);
        return new ApiResponse<>(res);
    }

    /**
     * API phê duyệt tài khoản trường đại học.
     *
     * @param req Yêu cầu chứa thông tin cần thiết để phê duyệt tài khoản trường đại học.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/approved-university")
    public ApiResponse<UniversityApprovedResponse> approvedUniversity(@Valid @RequestBody UniversityApprovedRequest req){
        var res = universityService.approvedAccount(req);
        return new ApiResponse<>(res);
    }


    /**
     * API từ chối tài khoản trường đại học.
     *
     * @param req Yêu cầu chứa lý do và thông tin để từ chối tài khoản trường đại học.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/rejected-university")
    public ApiResponse<UniversityRejectedResponse> rejectedUniversity(@Valid @RequestBody UniversityRejectedRequest req){
        var res = universityService.rejectedAccount(req);
        return new ApiResponse<>(res);
    }

    /**
     * API phê duyệt tin tuyển dụng.
     *
     * @param req Yêu cầu chứa thông tin cần thiết để phê duyệt tin tuyển dụng.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/approved-job")
    public ApiResponse<JobApprovedResponse> approvedJob(@Valid @RequestBody JobApprovedRequest req) throws ParseException {
        var res = jobService.approved(req);
        return new ApiResponse<>(res);
    }

    /**
     * API từ chối tin tuyển dụng.
     *
     * @param req Yêu cầu chứa lý do và thông tin để từ chối tin tuyển dụng.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/rejected-job")
    public ApiResponse<JobRejectedResponse> rejectedJob(@Valid @RequestBody JobRejectedRequest req) throws ParseException {
        var res = jobService.rejected(req);
        return new ApiResponse<>(res);
    }

    /**
     * API phê duyệt hội thảo.
     *
     * @param req Yêu cầu chứa thông tin cần thiết để phê duyệt hội thảo.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/approved-workshop")
    public ApiResponse<WorkshopApprovedResponse> approvedWorkshop(@Valid @RequestBody WorkshopApprovedRequest req) throws ParseException {
        var res = workShopService.approved(req);
        return new ApiResponse<>(res);
    }

    /**
     * API từ chối hội thảo.
     *
     * @param req Yêu cầu chứa lý do và thông tin để từ chối hội thảo.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/rejected-workshop")
    public ApiResponse<WorkshopRejectedResponse> rejectedWorkshop(@Valid @RequestBody WorkshopRejectedRequest req) throws ParseException {
        var res = workShopService.rejected(req);
        return new ApiResponse<>(res);
    }
}
