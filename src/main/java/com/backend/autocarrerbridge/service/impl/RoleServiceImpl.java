package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.entity.Role;
import com.backend.autocarrerbridge.repository.RoleRepository;
import com.backend.autocarrerbridge.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;


    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

}
