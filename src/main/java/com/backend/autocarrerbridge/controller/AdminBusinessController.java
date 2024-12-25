package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.business.BusinessApprovedRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessRejectedRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.business.BusinessApprovedResponse;
import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.dto.response.business.BusinessRejectedResponse;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.service.BusinessService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * Controller xử lý các API dành riêng cho quản trị viên (admin v sub-admin)
 * để quản lý doanh nghiệp.
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUB_ADMIN')")
@SecurityRequirement(name = "AdminAuthorization")
@RequestMapping("/api/admin")
public class AdminBusinessController {
    private final BusinessService businessService;

    /**
     * API phê duyệt tài khoản doanh nghiệp.
     *
     * @param req Yêu cầu chứa thông tin cần thiết để phê duyệt tài khoản doanh nghiệp.
     * @return ApiResponse phản hồi sau khi thực hiện thành công.
     */
    @PostMapping("/approved-business")
    public ApiResponse<BusinessApprovedResponse> approvedBusiness(@Valid @RequestBody BusinessApprovedRequest req) {
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
    public ApiResponse<BusinessRejectedResponse> rejectedBusiness(@Valid @RequestBody BusinessRejectedRequest req) {
        var res = businessService.rejectedAccount(req);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách tất cả doanh nghiệp với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách doanh nghiệp.
     */
    @GetMapping("/get-all-businesses")
    public ApiResponse<PagingResponse<BusinessResponse>> getAllBusinesses(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "keyword", required = false) String keyword) {
        var res = businessService.getAllBusinesses(PageInfo.of(pageNo, pageSize, keyword));

        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách doanh nghiệp đã được phê duyệt với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách doanh nghiệp đã được phê duyệt.
     */
    @GetMapping("/get-approved-businesses")
    public ApiResponse<PagingResponse<BusinessResponse>> getApprovedBusinesses(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "keyword", required = false) String keyword) {
        var res = businessService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.APPROVED);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách doanh nghiệp đang chờ phê duyệt với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách doanh nghiệp đang chờ phê duyệt.
     */
    @GetMapping("/get-pending-businesses")
    public ApiResponse<PagingResponse<BusinessResponse>> getPendingBusinesses(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "keyword", required = false) String keyword) {
        var res = businessService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.PENDING);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách doanh nghiệp đã bị từ chối với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách doanh nghiệp đã bị từ chối.
     */
    @GetMapping("/get-rejected-businesses")
    public ApiResponse<PagingResponse<BusinessResponse>> getRejectedBusinesses(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "keyword", required = false) String keyword) {
        var res = businessService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.REJECTED);
        return new ApiResponse<>(res);
    }
}
