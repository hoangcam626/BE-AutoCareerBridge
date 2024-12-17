package com.backend.autocarrerbridge.controller;

import java.text.ParseException;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.employee.EmployeeRequest;
import com.backend.autocarrerbridge.dto.response.employee.EmployeeResponse;
import com.backend.autocarrerbridge.service.EmployeeService;
import com.backend.autocarrerbridge.util.Constant;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/employees")
public class EmployeeController {
    EmployeeService employeeService;

    /**
     * API tạo mới nhân viên.
     * @apiNote Sử dụng để thêm một nhân viên mới vào hệ thống.
     * @param request Dữ liệu thông tin của nhân viên cần thêm.
     * @return Thông tin chi tiết của nhân viên vừa được tạo.
     */
    @PostMapping("/create")
    ApiResponse<EmployeeResponse> createEmployee(@Valid EmployeeRequest request) {
        return ApiResponse.<EmployeeResponse>builder()
                .data(employeeService.addEmployee(request))
                .build();
    }

    /**
     * API lấy danh sách tất cả nhân viên.
     * @apiNote Sử dụng để truy vấn tất cả nhân viên từ cơ sở dữ liệu.
     * @return Danh sách thông tin của tất cả nhân viên.
     * @throws ParseException Nếu dữ liệu không thể phân tích được.
     */
    @GetMapping("/get-all")
    ApiResponse<List<EmployeeResponse>> getAllEmployee() throws ParseException {
        return ApiResponse.<List<EmployeeResponse>>builder()
                .data(employeeService.getListEmployeee())
                .build();
    }

    /**
     * API lấy thông tin chi tiết của một nhân viên.
     * @apiNote Sử dụng để lấy thông tin của một nhân viên theo mã nhân viên.
     * @param employeeId Mã của nhân viên cần lấy thông tin.
     * @return Thông tin chi tiết của nhân viên được yêu cầu.
     */
    @GetMapping("/{employeeId}")
    ApiResponse<EmployeeResponse> getEmployee(@PathVariable("employeeId") Integer employeeId) {
        return ApiResponse.<EmployeeResponse>builder()
                .data(employeeService.getEmployeeById(employeeId))
                .build();
    }

    /**
     * API cập nhật thông tin nhân viên.
     * @apiNote Sử dụng để thay đổi thông tin của nhân viên theo mã nhân viên.
     * @param employeeId Mã của nhân viên cần cập nhật.
     * @param request Dữ liệu thông tin mới của nhân viên.
     * @return Thông tin chi tiết của nhân viên sau khi cập nhật.
     */
    @PutMapping("/{employeeId}")
    ApiResponse<EmployeeResponse> updateEmployee(@PathVariable Integer employeeId, @Valid EmployeeRequest request) {
        return ApiResponse.<EmployeeResponse>builder()
                .data(employeeService.updateEmployee(employeeId, request))
                .build();
    }

    /**
     * API xóa nhân viên.
     * @apiNote Sử dụng để xóa một nhân viên khỏi hệ thống theo mã nhân viên.
     * @param employeeId Mã của nhân viên cần xóa.
     * @return Thông báo thành công sau khi xóa nhân viên.
     */
    @DeleteMapping("/{employeeId}")
    ApiResponse<String> deleteEmployee(@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ApiResponse.<String>builder().data(Constant.SUCCESS_MESSAGE).build();
    }

    //phan trang get list
    @GetMapping("/get-all-employee-of-business")
    public ApiResponse<Object> getEmployeePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String keyword) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ApiResponse.builder()
                .data(employeeService.getAllEmployeeOfBusinessPage(keyword,pageable))
                .build();
    }
}
