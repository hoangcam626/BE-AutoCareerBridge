package com.backend.autocarrerbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {}
