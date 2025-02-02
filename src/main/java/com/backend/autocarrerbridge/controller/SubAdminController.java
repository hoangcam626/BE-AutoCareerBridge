package com.backend.autocarrerbridge.controller;

import java.text.ParseException;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminCreateRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminDeleteRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminSelfRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminUpdateRequest;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminCreateResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminDeleteResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminSelfResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminUpdateResponse;
import com.backend.autocarrerbridge.service.SubAdminService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * SubAdminController xử lý các yêu cầu API liên quan đến quản lý sub-admin.
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/sub-admin")
public class SubAdminController {
    private final SubAdminService subAdminService;

    /**
     * API tạo một sub-admin mới.
     *
     * @param req - Dữ liệu yêu cầu, bao gồm thông tin sub-admin và ảnh đại diện.
     * @return ApiResponse chứa thông tin sub-admin vừa được tạo.
     * @throws ParseException - Nếu xảy ra lỗi trong quá trình xử lý.
     */
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content =
            @Content(
                    mediaType = "multipart/form-data",
                    schema = @Schema(implementation = SubAdminCreateRequest.class)))
    @PostMapping("/create")
    public ApiResponse<SubAdminCreateResponse> create(@Valid @ModelAttribute SubAdminCreateRequest req)
            throws ParseException {

        var res = subAdminService.create(req);
        return new ApiResponse<>(res);
    }

    /**
     * API cập nhật thông tin của một sub-admin.
     *
     * @param req - Dữ liệu yêu cầu, bao gồm các trường thông tin cần được cập nhật.
     * @return ApiResponse xác nhận quá trình cập nhật thành công.
     * @throws ParseException - Nếu xảy ra lỗi trong quá trình xử lý.
     */
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content =
            @Content(
                    mediaType = "multipart/form-data",
                    schema = @Schema(implementation = SubAdminUpdateRequest.class)))
    @PutMapping("/update")
    public ApiResponse<SubAdminUpdateResponse> update(@Valid SubAdminUpdateRequest req) throws ParseException {
        var res = subAdminService.update(req);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy thông tin chi tiết của một sub-admin.
     *
     * @param id - ID của sub-admin cần xem chi tiết.
     * @return ApiResponse chứa thông tin chi tiết của sub-admin.
     */
    @GetMapping("/get-detail")
    public ApiResponse<SubAdminSelfResponse> detail(@RequestParam(value = "id", required = false) Integer id) {
        var res = subAdminService.detail(SubAdminSelfRequest.of(id));
        return new ApiResponse<>(res);
    }

    /**
     * API xóa một sub-admin.
     *
     * @param id - Yêu cầu xóa, bao gồm ID của sub-admin cần xóa.
     * @return ApiResponse xác nhận sub-admin đã được chuyển sang trạng thái INACTIVE.
     */
    @DeleteMapping("/delete")
    public ApiResponse<SubAdminDeleteResponse> delete(@RequestParam(value = "id", required = false) Integer id) {
        var res = subAdminService.delete(SubAdminDeleteRequest.of(id));
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách toàn bộ sub-admin.
     *
     * @return ApiResponse chứa danh sách tất cả các sub-admin đang hoạt động.
     */
    @GetMapping("/get-all")
    public ApiResponse<List<SubAdminSelfResponse>> getSubAdmins() {
        var res = subAdminService.listSubAdmins();
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách sub-admin theo phân trang.
     *
     * @param pageNo   - Số trang cần lấy (bắt đầu từ 0).
     * @param pageSize - Số lượng bản ghi trên mỗi trang.
     * @return ApiResponse chứa danh sách sub-admin trong trang yêu cầu.
     */
    @GetMapping("/get-paging")
    public ApiResponse<Page<SubAdminSelfResponse>> getSubAdmins(
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword) {
        var res = subAdminService.pageSubAdmins(PageInfo.of(pageNo, pageSize, keyword));
        return new ApiResponse<>(res);
    }
}
