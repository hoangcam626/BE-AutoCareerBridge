package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.request.EmployeeRequest;
import com.backend.autocarrerbridge.dto.request.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.EmployeeResponse;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Employee;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.mapper.EmployeeMapper;
import com.backend.autocarrerbridge.repository.EmployeeRepository;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.EmployeeService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
    UserAccountService userAccountService;
    TokenService tokenService;
    BusinessService businessService;

    @Override
    public List<EmployeeResponse> getListEmployeee() {
        var employees = employeeRepository.findAll();
        return employees.stream().map(employeeMapper::toEmployeeResponse).toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Integer id) {
        return employeeMapper.toEmployeeResponse(employeeRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ERROR_USER_NOT_FOUND)));
    }

//    @PreAuthorize("hasRole('')")
    @Override
    public EmployeeResponse addEmployee(EmployeeRequest request, String token) {
        Employee employee=employeeMapper.toEmployee(request);
        try {
            String emailBusiness= tokenService.getClaim(token,"sub");
            Business business = businessService.findBusinessByEmail(emailBusiness);
            employee.setBusiness(business);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

//        tao tai khoan cho nhan vien
        UserAccountRequest userAccountRequest=UserAccountRequest.builder()
                .username(employee.getEmail())
                .password("1234546")
                .build();
        UserAccount accountEmployee=userAccountService.createUser(userAccountRequest);

        employee.setUserAccount(accountEmployee);

        try {
            employee=employeeRepository.save(employee);
        }catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.ERROR_USER_EXITED);
        }
        return employeeMapper.toEmployeeResponse(employee);
    }

    @Override
    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest request) {
        Employee employee=employeeRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ERROR_USER_NOT_FOUND));
        employeeMapper.udpateEmployee(employee,request);
        return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee employee=employeeRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ERROR_USER_NOT_FOUND));
        employee.setStatus(Status.INACTIVE);
    }
}
