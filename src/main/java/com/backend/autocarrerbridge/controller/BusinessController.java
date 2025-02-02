package com.backend.autocarrerbridge.controller;

import static com.backend.autocarrerbridge.util.Constant.REGISTER_BUSINESS;
import static com.backend.autocarrerbridge.util.Constant.SEND_CODE;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.account.UserBusinessRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessUpdateRequest;
import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.util.Constant;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Controller xử lý các API liên quan đến quản lý doanh nghiệp.
 */
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/business")
public class BusinessController {
    BusinessService businessService;

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
    @PostMapping("/verify-business")
    public ApiResponse<Object> verifyBusiness(@ModelAttribute @Valid UserBusinessRequest userBusinessRequest){
        return ApiResponse.builder()
                .code(SUCCESS)
                .message(SEND_CODE)
                .data(businessService.generateEmailCode(userBusinessRequest))
                .build();
    }
    /**
     * API cập nhật thông tin doanh nghiệp.
     * @apiNote Sử dụng để chỉnh sửa thông tin của một doanh nghiệp dựa trên ID doanh nghiệp.
     * @param businessId ID của doanh nghiệp cần cập nhật.
     * @param request Thông tin cần chỉnh sửa của doanh nghiệp.
     * @return Thông tin doanh nghiệp sau khi được cập nhật.
     */
    @PostMapping("/{businessId}")
    ApiResponse<BusinessResponse> updateBusiness(
            @PathVariable Integer businessId, @Valid BusinessUpdateRequest request) {
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
    @GetMapping("/get-by-id/{businessId}")
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
        return ApiResponse.<String>builder().data(Constant.SUCCESS_MESSAGE).build();
    }
    @GetMapping("/feature-business")
    ApiResponse<Object> getTopFeatureBusiness(@RequestParam(required = false) Integer industryId) {
       return ApiResponse.builder()
               .data(businessService.getFeatureBusiness(industryId, PageRequest.of(0,9)))
               .build();
    }
    @GetMapping("/list-home")
    ApiResponse<Object> getBusinessListHome() {
        return ApiResponse.builder()
                .data(businessService.getBusinessListHome())
                .build();
    }
    @GetMapping("/get-all-business-page")
    public ApiResponse<Object> getAllBusinessPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String keyword
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ApiResponse.builder()
                .data(businessService.getAllBusinessPage(keyword, pageable))
                .build();
    }

    @GetMapping("/countTotalBusiness")
    public ApiResponse<Object> getTotalBusiness() {
        return ApiResponse.builder().data(businessService.countBusiness()).build();
    }
}
