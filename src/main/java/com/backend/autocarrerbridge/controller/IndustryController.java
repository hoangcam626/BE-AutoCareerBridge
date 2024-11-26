package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.request.industry.IndustryRequest;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.service.IndustryService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("api/industry")
@Setter
@Getter
@RequiredArgsConstructor
@CrossOrigin("*")
public class IndustryController {
    private final IndustryService industryService;

    /**
     * API lấy danh sách tất cả ngành nghề đã phân trang.
     * @apiNote được sử dụng để truy vấn tất cả các bản ghi ngành nghề từ cơ sở dữ liệu.
     */
    @GetMapping("/get-all-paging")
    public ApiResponse<Object> getAllIndustryPaging(
            @RequestParam(defaultValue = "0") int first,
            @RequestParam(defaultValue = "10") int rows,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code) {
        return industryService.getAllIndustryPaging(first, rows, page, name, code);
    }

    /**
     * API lấy danh sách tất cả ngành nghề.
     * @apiNote được sử dụng để truy vấn tất cả các bản ghi ngành nghề từ cơ sở dữ liệu.
     */
    @GetMapping("/get-all")
    public ApiResponse<Object> getAllIndustry() {
        return industryService.getAllIndustry();
    }

    /**
     * API để tạo mới một ngành nghề.
     * @apiNote được sử dụng để thêm một ngành nghề mới vào cơ sở dữ liệu.
     */
    @PostMapping("/create")
    public ApiResponse<Object> createIndustry(@RequestBody IndustryRequest industryRequest) throws ParseException {
        return industryService.createIndustry(industryRequest);
    }


    /**
     * API để cập nhật thông tin của ngành nghề
     * @apiNote được sử dụng để cập nhật một ngành nghề mới vào cơ sở dữ liệu.
     */
    @PutMapping("/update")
    public ApiResponse<Object> updateIndustry(@RequestBody IndustryRequest industryRequest) throws ParseException {
        return industryService.updateIndustry(industryRequest);
    }

    /**
     * API để vô hiệu hóa một ngành nghề.
     * @apiNote được sử dụng để đánh dấu một ngành nghề là không còn hoạt động.
    */
    @PutMapping("/inactive")
    public ApiResponse<Object> inactiveIndustry(@RequestParam Integer id) throws ParseException {
        return industryService.inactiveIndustry(id);
    }
}
