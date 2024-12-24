package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.response.university.AdminUniversityResponse;
import jakarta.validation.Valid;

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
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityApprovedResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityRejectedResponse;
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
    @GetMapping("/get-all-universities")
    public ApiResponse<PagingResponse<AdminUniversityResponse>> getAllUniversities(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                                   @RequestParam(value = "keyword", required = false) String keyword) {
        var res = universityService.getAllUniversities(PageInfo.of(pageNo, pageSize, keyword));
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
    @GetMapping("/get-approved-universities")
    public ApiResponse<PagingResponse<AdminUniversityResponse>> getApprovedUniversities(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                         @RequestParam(value = "keyword", required = false) String keyword) {
        var res = universityService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.APPROVED);
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
    @GetMapping("/get-pending-universities")
    public ApiResponse<PagingResponse<AdminUniversityResponse>> getPendingUniversities(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                        @RequestParam(value = "keyword", required = false) String keyword) {
        var res = universityService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.PENDING);
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
    @GetMapping("/get-rejected-universities")
    public ApiResponse<PagingResponse<AdminUniversityResponse>> getRejectedUniversities(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                         @RequestParam(value = "keyword", required = false) String keyword) {
        var res = universityService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.REJECTED);
        return new ApiResponse<>(res);
    }

    @GetMapping("/get-detail-university")
    public ApiResponse<AdminUniversityResponse> getUniversity(@RequestParam(value = "id", required = true) Integer id) {
        var res = universityService.detail(id);
        return new ApiResponse<>(res);
    }
}