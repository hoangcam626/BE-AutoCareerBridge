package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import jakarta.validation.Valid;

import java.text.ParseException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopApprovedRequest;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopRejectedRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopApprovedResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopRejectedResponse;
import com.backend.autocarrerbridge.service.WorkShopService;
import com.backend.autocarrerbridge.util.enums.State;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * Controller xử lý các API dành riêng cho quản trị viên (admin v sub-admin)
 * để quản lý hội thaảo của trường học.
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUB_ADMIN')")
@SecurityRequirement(name = "AdminAuthorization")
@RequestMapping("/api/admin")
public class AdminWorkshopController {
    private final WorkShopService workShopService;

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

    @GetMapping("/approved-workshops")
    public ApiResponse<PagingResponse<WorkShopResponse>> getApprovedWorkshops(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                           @RequestParam(value = "keyword", required = false) String keyword) {
        var res = workShopService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.APPROVED);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách hội thảo đang chờ phê duyệt với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách hội thảo đang chờ phê duyệt.
     */
    @GetMapping("/pending-workshops")
    public ApiResponse<PagingResponse<WorkShopResponse>> getPendingWorkshops(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                   @RequestParam(value = "keyword", required = false) String keyword) {
        var res = workShopService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.PENDING);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách hội thảo đã bị từ chối với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách hội thảo đã bị từ chối.
     */
    @GetMapping("/rejected-workshops")
    public ApiResponse<PagingResponse<WorkShopResponse>> getRejectedWorkshops(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                    @RequestParam(value = "keyword", required = false) String keyword) {
        var res = workShopService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.REJECTED);
        return new ApiResponse<>(res);
    }
}
