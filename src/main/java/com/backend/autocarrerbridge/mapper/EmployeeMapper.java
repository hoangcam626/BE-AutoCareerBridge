package com.backend.autocarrerbridge.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.backend.autocarrerbridge.dto.request.EmployeeRequest;
import com.backend.autocarrerbridge.dto.response.EmployeeResponse;
import com.backend.autocarrerbridge.entity.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEmployee(EmployeeRequest request);

    EmployeeResponse toEmployeeResponse(Employee employee);

    void udpateEmployee(@MappingTarget Employee employee, EmployeeRequest request);
}
