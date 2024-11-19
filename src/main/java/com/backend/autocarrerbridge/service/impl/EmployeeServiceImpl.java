package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.request.EmployeeRequest;
import com.backend.autocarrerbridge.dto.request.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.EmployeeResponse;
import com.backend.autocarrerbridge.dto.response.UserAccountResponse;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Employee;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.mapper.EmployeeMapper;
import com.backend.autocarrerbridge.mapper.UserAccountMapper;
import com.backend.autocarrerbridge.repository.EmployeeRepository;
import com.backend.autocarrerbridge.service.EmployeeService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
    UserAccountService userAccountService;
    UserAccountMapper userAccountMapper;

    @Override
    public List<EmployeeResponse> getListEmployeee() {
        var employees = employeeRepository.findAll();
        return employees.stream().map(employeeMapper::toEmployeeResponse).toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Integer id) {
        return employeeMapper.toEmployeeResponse(employeeRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ERROR_USER_NOT_FOUND)));
    }

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest request) {
        Employee employee=employeeMapper.toEmployee(request);
//        tao tai khoan cho nhan vien
        UserAccountRequest userAccountRequest=UserAccountRequest.builder()
                .username(employee.getEmail())
                .password("1234546")
                .build();
        UserAccountResponse userAccountResponse=userAccountService.createUser(userAccountRequest);

        employee.setUserAccount(userAccountMapper.toUserAccount(userAccountResponse));

//        fake du lieeuj
        employee.setBusiness(new Business(1,"HHHH","1241HHF","1000","hasdf.com",1990,"HH@gmail.com"
                ,"0239431412","cong ty thanh lap nam",1,1,null,null));

//
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
