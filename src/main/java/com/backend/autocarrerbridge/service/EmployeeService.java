package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.EmployeeRequest;
import com.backend.autocarrerbridge.dto.response.EmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EmployeeService {
    List<EmployeeResponse> getListEmployeee();
    EmployeeResponse getEmployeeById(Integer id);
    EmployeeResponse addEmployee(EmployeeRequest request, String token);
    EmployeeResponse updateEmployee(Integer id,EmployeeRequest request);
    void deleteEmployee(Integer id);
}
