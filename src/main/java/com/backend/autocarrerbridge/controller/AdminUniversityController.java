package com.backend.autocarrerbridge.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.request.university.UniversityApprovedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRejectedRequest;
import com.backend.autocarrerbridge.dto.response.university.UniversityApprovedResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityRejectedResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.service.UniversityService;
import com.backend.autocarrerbridge.util.enums.State;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * Controller xử lý các API dành riêng cho quản trị viên (admin v sub-admin)
 * để quản lý trường học.
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUB_ADMIN')")
@SecurityRequirement(name = "AdminAuthorization")
@RequestMapping("/api/admin")
public class AdminUniversityController {
    private final UniversityService universityService;

    /**
     * API phê duyệt tài khoản trường đại học.
     *
     * @param req Yêu cầu chứa thông tin cần thiết để phê duyệt tài khoản trường đại học.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/approved-university")
    public ApiResponse<UniversityApprovedResponse> approvedUniversity(@Valid @RequestBody UniversityApprovedRequest req) {
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
    public ApiResponse<UniversityRejectedResponse> rejectedUniversity(@Valid @RequestBody UniversityRejectedRequest req) {
        var res = universityService.rejectedAccount(req);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách trường đại học đã được phê duyệt với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách trường đại học đã được phê duyệt.
     */
    @GetMapping("/approved-universities")
    public ApiResponse<Page<UniversityResponse>> getApprovedUniversities(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                         @RequestParam(value = "keyword", required = false) String keyword) {
        var res = universityService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.APPROVED.getValue());
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách trường đại học đang chờ phê duyệt với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách trường đại học đang chờ phê duyệt.
     */
    @GetMapping("/pending-universities")
    public ApiResponse<Page<UniversityResponse>> getPendingUniversities(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                        @RequestParam(value = "keyword", required = false) String keyword) {
        var res = universityService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.PENDING.getValue());
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách trường đại học đã bị từ chối với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách trường đại học đã bị từ chối.
     */
    @GetMapping("/rejected-universities")
    public ApiResponse<Page<UniversityResponse>> getRejectedUniversities(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                         @RequestParam(value = "keyword", required = false) String keyword) {
        var res = universityService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.REJECTED.getValue());
        return new ApiResponse<>(res);
    }
}