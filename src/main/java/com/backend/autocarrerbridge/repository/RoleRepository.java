package com.backend.autocarrerbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
