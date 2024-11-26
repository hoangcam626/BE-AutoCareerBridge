package com.backend.autocarrerbridge.mapper;

import com.backend.autocarrerbridge.dto.request.business.BusinessUpdateRequest;
import com.backend.autocarrerbridge.dto.request.employee.EmployeeRequest;
import com.backend.autocarrerbridge.entity.Employee;
import org.mapstruct.Mapper;

import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.entity.Business;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BusinessMapper {
    BusinessResponse toBusinessResponse(Business business);

    void udpateBusiness(@MappingTarget Business business, BusinessUpdateRequest request);
}
