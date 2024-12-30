package com.backend.autocarrerbridge.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.backend.autocarrerbridge.dto.request.employee.EmployeeRequest;
import com.backend.autocarrerbridge.dto.response.employee.EmployeeResponse;
import com.backend.autocarrerbridge.entity.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEmployee(EmployeeRequest request);

    EmployeeResponse toEmployeeResponse(Employee employee);

    void updateEmployee(@MappingTarget Employee employee, EmployeeRequest request);
}
