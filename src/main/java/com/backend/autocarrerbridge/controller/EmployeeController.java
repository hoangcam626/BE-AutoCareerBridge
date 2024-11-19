package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.request.EmployeeRequest;
import com.backend.autocarrerbridge.dto.response.EmployeeResponse;
import com.backend.autocarrerbridge.mapper.EmployeeMapper;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.service.EmployeeService;
import com.backend.autocarrerbridge.util.Constant;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("employees")
public class EmployeeController {
    EmployeeService employeeService;
    EmployeeMapper employeeMapper;

    @PostMapping("/create")
    ApiResponse<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeRequest request){
        return ApiResponse.<EmployeeResponse>builder()
                .data(employeeService.addEmployee(request))
                .build();
    }

    @GetMapping("/getAll")
    ApiResponse<List<EmployeeResponse>> getAllEmployee(){
        return ApiResponse.<List<EmployeeResponse>>builder()
                .data(employeeService.getListEmployeee())
                .build();
    }

    @GetMapping("/{employeeId}")
    ApiResponse<EmployeeResponse> getEmployee(@PathVariable("employeeId") Integer employeeId){
        return ApiResponse.<EmployeeResponse>builder()
                .data(employeeService.getEmployeeById(employeeId))
                .build();
    }

    @PutMapping("/{employeeId}")
    ApiResponse<EmployeeResponse> updateEmployee(@PathVariable Integer employeeId, @RequestBody EmployeeRequest request){
        return ApiResponse.<EmployeeResponse>builder()
                .data(employeeService.updateEmployee(employeeId,request))
                .build();
    }

    @DeleteMapping("/{employeeId}")
    ApiResponse<String> deleteEmployee(@PathVariable Integer employeeId){
        employeeService.deleteEmployee(employeeId);
        return ApiResponse.<String>builder()
                .data(Constant.SUCCESS_MESSAGE)
                .build();
    }
}