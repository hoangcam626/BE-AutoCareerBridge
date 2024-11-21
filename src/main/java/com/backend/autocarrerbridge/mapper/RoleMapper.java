package com.backend.autocarrerbridge.mapper;

import com.backend.autocarrerbridge.dto.request.RoleRequest;
import com.backend.autocarrerbridge.dto.response.RoleResponse;
import com.backend.autocarrerbridge.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);

}
