package com.backend.autocarrerbridge.service.impl;

import java.text.ParseException;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.EmployeeRequest;
import com.backend.autocarrerbridge.dto.response.EmployeeResponse;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Employee;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.mapper.EmployeeMapper;
import com.backend.autocarrerbridge.mapper.UserAccountMapper;
import com.backend.autocarrerbridge.repository.EmployeeRepository;
import com.backend.autocarrerbridge.service.*;
import com.backend.autocarrerbridge.util.enums.PredefinedRole;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
    UserAccountService userAccountService;
    TokenService tokenService;
    BusinessService businessService;
    UserAccountMapper userAccountMapper;
    RoleService roleService;

    @Override
    public List<EmployeeResponse> getListEmployeee(String token) {
        try {
            //            set Business fo employee
            String emailBusiness = tokenService.getSub(token);
            Business business = businessService.findByEmail(emailBusiness);
            //            employee.setBusiness(business);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        var employees = employeeRepository.findAll();
        return employees.stream().map(employeeMapper::toEmployeeResponse).toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Integer id) {
        return employeeMapper.toEmployeeResponse(
                employeeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_USER_NOT_FOUND)));
    }

    //    @PreAuthorize("hasRole('')")
    @Transactional
    @Override
    public EmployeeResponse addEmployee(EmployeeRequest request, String token) {
        Employee employee = employeeMapper.toEmployee(request);

        try {
            //            set Business fo employee
            String emailBusiness = tokenService.getSub(token);
            Business business = businessService.findByEmail(emailBusiness);
            employee.setBusiness(business);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //        create UserAccount for Employee
        UserAccount userAccount = UserAccount.builder()
                .username(employee.getEmail())
                .password("1234546")
                .role(roleService.findById(PredefinedRole.EMPLOYEE.getValue()))
                .state(State.APPROVED)
                .build();
        UserAccount accountEmployee = userAccountService.registerUser(userAccount);
        employee.setUserAccount(accountEmployee);

        //        Save Employee
        try {
            employee = employeeRepository.save(employee);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.ERROR_USER_EXITED);
        }
        EmployeeResponse employeeResponse = employeeMapper.toEmployeeResponse(employee);
        employeeResponse.setBusinessId(employee.getBusiness().getId());
        userAccount.setRole(employee.getUserAccount().getRole());
        employeeResponse.setUserAccount(userAccountMapper.toUserAccountResponse(employee.getUserAccount()));

        return employeeResponse;
    }

    @Override
    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest request) {
        Employee employee =
                employeeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_USER_NOT_FOUND));
        employeeMapper.udpateEmployee(employee, request);
        return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee employee =
                employeeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_USER_NOT_FOUND));
        employee.setStatus(Status.INACTIVE);
    }
}
