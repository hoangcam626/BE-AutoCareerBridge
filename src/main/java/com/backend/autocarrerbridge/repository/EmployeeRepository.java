package com.backend.autocarrerbridge.repository;

import java.util.List;

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
}
