package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import jakarta.validation.Valid;

import java.text.ParseException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.job.JobApprovedRequest;
import com.backend.autocarrerbridge.dto.request.job.JobRejectedRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.job.JobApprovedResponse;
import com.backend.autocarrerbridge.dto.response.job.JobRejectedResponse;
import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.service.JobService;
import com.backend.autocarrerbridge.util.enums.State;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * Controller xử lý các API dành riêng cho quản trị viên (admin v sub-admin)
 * để quản lý tin tuyển dụng.
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUB_ADMIN')")
@SecurityRequirement(name = "AdminAuthorization")
@RequestMapping("/api/admin")
public class AdminJobController {
    private final JobService jobService;

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
     * API lấy danh sách tin tuyển dụng đã được phê duyệt với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách tin tuyển dụng đã được phê duyệt.
     */
    @GetMapping("/approved-jobs")
    public ApiResponse<PagingResponse<JobResponse>> getApprovedJobs(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                    @RequestParam(value = "keyword", required = false) String keyword) {
        var res = jobService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.APPROVED.getValue());
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách tin tuyển dụng đang chờ phê duyệt với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách tin tuyển dụng đang chờ phê duyệt.
     */
    @GetMapping("/pending-jobs")
    public ApiResponse<PagingResponse<JobResponse>> getPendingJobs(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                   @RequestParam(value = "keyword", required = false) String keyword) {
        var res = jobService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.PENDING.getValue());
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách tin tuyển dụng đã bị từ chối với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách tin tuyển dụng đã bị từ chối.
     */
    @GetMapping("/rejected-jobs")
    public ApiResponse<PagingResponse<JobResponse>> getRejectedJobs(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                    @RequestParam(value = "keyword", required = false) String keyword) {
        var res = jobService.getPagingByState(PageInfo.of(pageNo, pageSize, keyword), State.REJECTED.getValue());
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách tất cả tin tuyển dụng với thông tin phân trang.
     *
     * @param pageNo   Số trang cần lấy, mặc định là 0.
     * @param pageSize Số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param keyword  Từ khóa tìm kiếm (không bắt buộc).
     * @return ApiResponse chứa danh sách tin tuyển dụng.
     */
    @GetMapping("/all-jobs")
    public ApiResponse<Object> getAllJobs(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                          @RequestParam(value = "keyword", required = false) String keyword) throws ParseException {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return jobService.getAllJob(keyword, pageable);
    }
}