package com.backend.autocarrerbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.autocarrerbridge.entity.Business;

import java.awt.print.Pageable;
import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Integer> {
    Business findByEmail(String email);

    /**
     * Lấy thông tin business qua employeeId
     */
    @Query("SELECT e.business FROM Employee e WHERE e.id = :employeeId")
    Business getBusinessByEmployeeId(Integer employeeId);

    @Query("select b from Business b WHERE b.userAccount.state = 1 and b.status <> 0")
    List<Business> findAllByStatus(Pageable pageable);
}
