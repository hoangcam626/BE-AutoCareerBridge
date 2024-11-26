package com.backend.autocarrerbridge.mapper;

import org.mapstruct.Mapper;

import com.backend.autocarrerbridge.dto.request.role.RoleRequest;
import com.backend.autocarrerbridge.dto.response.RoleResponse;
import com.backend.autocarrerbridge.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
