package com.backend.autocarrerbridge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.EmployeeRequest;
import com.backend.autocarrerbridge.dto.response.EmployeeResponse;

@Service
public interface EmployeeService {
    List<EmployeeResponse> getListEmployeee(String token);

    EmployeeResponse getEmployeeById(Integer id);

    EmployeeResponse addEmployee(EmployeeRequest request, String token);

    EmployeeResponse updateEmployee(Integer id, EmployeeRequest request);

    void deleteEmployee(Integer id);
}
