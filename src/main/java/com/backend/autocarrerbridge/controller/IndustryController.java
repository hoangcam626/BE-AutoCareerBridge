package com.backend.autocarrerbridge.controller;

import java.text.ParseException;
import java.util.List;

import com.backend.autocarrerbridge.dto.request.industry.DeleteIndustryRequest;
import com.backend.autocarrerbridge.dto.response.industry.BusinessIndustryDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.industry.IndustryRequest;
import com.backend.autocarrerbridge.service.IndustryService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping("api/industry")
@Setter
@Getter
@RequiredArgsConstructor
public class IndustryController {
    private final IndustryService industryService;

    /**
     * API lấy danh sách tất cả ngành nghề đã phân trang.
     *
     * @apiNote được sử dụng để truy vấn tất cả các bản ghi ngành nghề từ cơ sở dữ liệu.
     */
    @GetMapping("/get-all-paging")
    public ApiResponse<Object> getAllIndustryPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String keyword) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return industryService.getAllIndustryPaging(keyword, pageable);
    }

    /**
     * API lấy danh sách tất cả ngành nghề của Admin.
     *
     * @apiNote được sử dụng để truy vấn tất cả các bản ghi ngành nghề từ cơ sở dữ liệu.
     */
    @GetMapping("/get-all")
    public ApiResponse<Object> getAllIndustry() {
        return industryService.getAllIndustry();
    }

    /**
     * API để tạo mới một ngành nghề.
     *
     * @apiNote được sử dụng để thêm một ngành nghề mới vào cơ sở dữ liệu.
     */
    @PostMapping("/create")
    public ApiResponse<Object> createIndustry(@RequestBody IndustryRequest industryRequest) throws ParseException {
        return industryService.createIndustry(industryRequest);
    }

    /**
     * API để cập nhật thông tin của ngành nghề
     *
     * @apiNote được sử dụng để cập nhật một ngành nghề mới vào cơ sở dữ liệu.
     */
    @PutMapping("/update")
    public ApiResponse<Object> updateIndustry(@RequestParam Integer id, @RequestBody IndustryRequest industryRequest) throws ParseException {
        return industryService.updateIndustry(id,industryRequest);
    }

    /**
     * API để vô hiệu hóa một ngành nghề.
     *
     * @apiNote được sử dụng để đánh dấu một ngành nghề là không còn hoạt động.
     */
    @PutMapping("/inactive")
    public ApiResponse<Object> inactiveIndustry(@RequestParam Integer id) throws ParseException {
        return industryService.inactiveIndustry(id);
    }

    /**
     * API để thêm ngành nghề vào doanh nghiệp.
     *
     * @apiNote được sử dụng để thêm một ngành nghề vào doanh nghiệp.
     */
    @PostMapping("/create-to-business")
    public ApiResponse<Object> createIndustryToBusiness(@RequestParam(value = "id") Integer id) throws ParseException {
        return industryService.createIndustryToBusiness(id);
    }

    /**
     * API lấy danh sách ngành nghề của doanh nghiệp.
     *
     * @apiNote được sử dụng để thêm một ngành nghề vào doanh nghiệp.
     */
    @GetMapping("/get-all-industry-business")
    public ApiResponse<Object> getIndustryOfBusiness(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String keyword)
            throws ParseException {
        Pageable pageable = PageRequest.of(page - 1, size);
        return industryService.getIndustryOfBusiness(keyword, pageable);
    }

    /**
     * API lấy danh sách ngành nghề của doanh nghiệp.
     *
     * @apiNote được sử dụng để thêm một ngành nghề vào doanh nghiệp.
     */
    @GetMapping("/get-all-industry-business-no-pag")
    public ApiResponse<Object> getIndustryOfBusinessNoPag() throws ParseException {
        return industryService.getIndustryOfBusinessNoPag();
    }

    /**
     *  Lấy danh sách ngaành nghề của doanh nghiệp chỉ định
     */
    @GetMapping("/get-all-industry-of-business")
    public ApiResponse<List<BusinessIndustryDto>> getIndustryOfBusiness(@RequestParam(value = "businessId") Integer businessId) {
        var res = industryService.getIndustryOfBusiness(businessId);
        return new ApiResponse<>(res);
    }
    /**
     * API Xem chi tiết ngành nghề của doanh nghiệp.
     *
     * @apiNote được sử dụng xem chi tiết ngành nghề của doanh nghiệp.
     */
    @GetMapping("/get-detail")
    public ApiResponse<Object> getIndustryDetailOfBusiness(@RequestParam(value = "id") Integer id) throws ParseException {
        return industryService.getIndustryDetailOfBusiness(id);
    }

    /**
     * API Xem chi tiết ngành nghề .
     *
     * @apiNote được sử dụng xem chi tiết ngành nghề của doanh nghiệp.
     */
    @GetMapping("/get-detail-admin")
    public ApiResponse<Object> getIndustryDetail(@RequestParam(value = "id") Integer id) throws ParseException {
        return industryService.getIndustryDetail(id);
    }
    /**
     * API Xóa ngành nghề khỏi doanh nghiệp.
     *
     * @apiNote được sử dụng xóa ngành nghề của doanh nghiệp.
     */
    @PostMapping("/delete")
    public ApiResponse<Object> deleteBusinessIndustry(@RequestBody DeleteIndustryRequest deleteIndustryRequest) throws ParseException {
        return industryService.inactiveIndustryOfBusiness(deleteIndustryRequest);
    }

    @GetMapping("/check")
    public ApiResponse<Object> checkIndustry(@RequestParam Integer industryId) throws ParseException {
        return industryService.checkIndustryExist(industryId);
    }

    /**
     * API thống kê ngành nghề được sử dụng nhiều
     *
     * @apiNote được sử dụng để truy vấn ngành nghề từ cơ sở dữ liệu.
     */
    @GetMapping("/get-used-industry")
    public ApiResponse<Object> getUsedIndustry() throws ParseException {
        return industryService.getMostUsedIndustry();
    }

    /**
     * API thống kê mức lương trung bình của từng ngành nghề
     *
     * @apiNote được sử dụng để truy vấn ngành nghề từ cơ sở dữ liệu.
     */
    @GetMapping("/get-average-salary")
    public ApiResponse<Object> getAverageSalaryByIndustry() throws ParseException {
        return industryService.getAverageSalaryByIndustry();
    }

}
