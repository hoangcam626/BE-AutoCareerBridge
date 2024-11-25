package com.backend.autocarrerbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.Business;
import org.springframework.data.jpa.repository.Query;

public interface BusinessRepository extends JpaRepository<Business, Integer> {
    Business findByEmail(String email);

    /**
     * Lấy thông tin business qua employeeId
     */
    @Query("SELECT e.business FROM Employee e WHERE e.id = :employeeId")
    Business getBusinessByEmployeeId(Integer employeeId);
}
