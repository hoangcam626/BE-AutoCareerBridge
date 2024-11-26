package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.request.account.UserBusinessRequest;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.business.BusinessUpdateRequest;
import com.backend.autocarrerbridge.dto.request.job.JobRequest;
import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.JobService;
import com.backend.autocarrerbridge.util.Constant;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

import static com.backend.autocarrerbridge.util.Constant.REGISTER_BUSINESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.account.UserBusinessRequest;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.JobService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Controller xử lý các API liên quan đến quản lý doanh nghiệp.
 */
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/business")
public class BusinessController {
    BusinessService businessService;
    JobService jobService;

    /**
     * API đăng ký thông tin doanh nghiệp.
     * @apiNote Sử dụng để thêm mới thông tin của doanh nghiệp vào hệ thống.
     * @param userBusinessRequest Thông tin của doanh nghiệp cần đăng ký.
     * @return Thông báo kết quả đăng ký và thông tin của doanh nghiệp vừa thêm.
     */
    @PostMapping("/register")
    public ApiResponse<Object> registerBusiness(@ModelAttribute @Valid UserBusinessRequest userBusinessRequest) {
        return ApiResponse.builder()
                .code(SUCCESS)
                .message(REGISTER_BUSINESS)
                .data(businessService.registerBusiness(userBusinessRequest))
                .build();
    }

    /**
     * API lấy danh sách tất cả công việc đã được đăng tuyển.
     * @apiNote Sử dụng để truy vấn danh sách công việc trong cơ sở dữ liệu.
     * @return Danh sách các công việc đã đăng tuyển.
     */
    @GetMapping("/get-all-job")
    private ApiResponse<Object> getAllJob() throws ParseException {
        return jobService.getAllJob();
    }

    /**
     * API để lấy chi tiết công việc đã đăng tuyển
     * @apiNote để truy vấn chi tiết công việc trong cơ sở dữ liệu
     */
    @GetMapping("/get-detail")
    private ApiResponse<Object> getDetail(@RequestParam Integer id) throws ParseException {
        return jobService.getJobDetail(id);
    }

    /**
     * API để tao công việc
     * @apiNote để để thêm công việc vào cơ sở dữ liệu
     */
    @PostMapping("/create-job")
    private ApiResponse<Object> createJob(@RequestBody JobRequest jobRequest) throws ParseException {
        return jobService.createJob(jobRequest);
    }

    /**
     * API để cập nhật công việc
     * @apiNote để để cập nhật thông tin công việc vào cơ sở dữ liệu
     */
    @PutMapping("/update-job")
    private ApiResponse<Object> updateJob(@RequestParam Integer jobId, @RequestBody JobRequest jobRequest) throws ParseException {
        return jobService.updateJob(jobId, jobRequest);
    }

    /**
     * API để vô hiệu hóa công việc
     * @apiNote để để vô hiệu hóa công việc đã đăng
     */
    @PutMapping("/inactive-job")
    private ApiResponse<Object> inactiveJob(@RequestParam Integer jobId) throws ParseException {
        return jobService.inactiveJob(jobId);
    }

    /**
     * API cập nhật thông tin doanh nghiệp.
     * @apiNote Sử dụng để chỉnh sửa thông tin của một doanh nghiệp dựa trên ID doanh nghiệp.
     * @param businessId ID của doanh nghiệp cần cập nhật.
     * @param request Thông tin cần chỉnh sửa của doanh nghiệp.
     * @return Thông tin doanh nghiệp sau khi được cập nhật.
     */
    @PostMapping("/{businessId}")
    ApiResponse<BusinessResponse> updateBusiness(@PathVariable Integer businessId,  @Valid BusinessUpdateRequest request) {
        return ApiResponse.<BusinessResponse>builder()
                .data(businessService.updateBusiness(businessId, request))
                .build();
    }

    /**
     * API lấy thông tin chi tiết của một doanh nghiệp.
     * @apiNote Sử dụng để lấy thông tin doanh nghiệp dựa trên ID doanh nghiệp.
     * @param businessId ID của doanh nghiệp cần lấy thông tin.
     * @return Thông tin chi tiết của doanh nghiệp.
     */
    @GetMapping("/{businessId}")
    ApiResponse<BusinessResponse> getBusiness(@PathVariable Integer businessId) {
        return ApiResponse.<BusinessResponse>builder()
                .data(businessService.getBusinessResponseById(businessId))
                .build();
    }

    /**
     * API lấy danh sách tất cả các doanh nghiệp.
     * @apiNote Sử dụng để lấy danh sách đầy đủ của tất cả các doanh nghiệp trong hệ thống.
     * @return Danh sách tất cả các doanh nghiệp.
     */
    @GetMapping("/get-all-business")
    ApiResponse<List<BusinessResponse>> getAllBusiness() {
        return ApiResponse.<List<BusinessResponse>>builder()
                .data(businessService.getListBusiness())
                .build();
    }

    /**
     * API xóa một doanh nghiệp.
     * @apiNote Sử dụng để xóa thông tin doanh nghiệp dựa trên ID doanh nghiệp.
     * @param businessId ID của doanh nghiệp cần xóa.
     * @return Thông báo kết quả sau khi xóa thành công.
     */
    @DeleteMapping("/{businessId}")
    ApiResponse<String> deleteBusiness(@PathVariable Integer businessId) {
        businessService.deleteBusiness(businessId);
        return ApiResponse.<String>builder()
                .data(Constant.SUCCESS_MESSAGE)
                .build();
    }
}
