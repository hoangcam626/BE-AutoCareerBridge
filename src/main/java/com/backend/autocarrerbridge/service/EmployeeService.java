package com.backend.autocarrerbridge.service;

import java.text.ParseException;
import java.util.List;

import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.util.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.employee.EmployeeRequest;
import com.backend.autocarrerbridge.dto.response.employee.EmployeeResponse;

@Service
public interface EmployeeService {
    List<EmployeeResponse> getListEmployeee() throws ParseException;

    EmployeeResponse getEmployeeById(Integer id);

    EmployeeResponse addEmployee(EmployeeRequest request);

    EmployeeResponse updateEmployee(Integer id, EmployeeRequest request);

    void restoreEmployee(Integer id) throws ParseException;

    void deleteEmployee(Integer id);

    PagingResponse<EmployeeResponse> getAllEmployeeOfBusinessPage(String keyword, Status status, Pageable pageable);
}
