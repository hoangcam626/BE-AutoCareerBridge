package com.backend.autocarrerbridge.controller.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Employee;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e JOIN e.business b WHERE b.email = :email")
    List<Employee> findEmployeesByBusinessEmail(@Param("email") String email);

    /**
     * Tìm employee qua username/email
     */
    @Query("SELECT e FROM Employee e JOIN e.userAccount u WHERE u.username = :username")
    Employee findByUsername(String username);

    Employee getEmployeeById(Integer id);

    @Query("select Max(id) as lastest_employee_id from Employee ")
    Integer getLastEmployee();

    @Query("SELECT e "
            + "FROM Employee e WHERE e.business.email = :email "
            + "AND e.status = com.backend.autocarrerbridge.util.enums.Status.ACTIVE "
            + "AND (:keyword IS NULL OR :keyword = '' "
            + "OR e.name like %:keyword% or e.employeeCode like %:keyword% or e.email like %:keyword%)"
            + "ORDER BY e.employeeCode ASC ")
    Page<Employee> getEmployeeForPaging(String email, @Param("keyword") String keyword, Pageable pageable);
}
