package com.backend.autocarrerbridge.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.autocarrerbridge.entity.Business;

public interface BusinessRepository extends JpaRepository<Business, Integer> {
    Business findByEmail(String email);

    /**
     * Lấy thông tin business qua employeeId
     */
    @Query("SELECT e.business FROM Employee e WHERE e.id = :employeeId")
    Business getBusinessByEmployeeId(Integer employeeId);

    /**
     * Tìm business qua username/email
     */
    @Query(
            """
				SELECT DISTINCT b\s
				FROM Business b\s
				LEFT JOIN b.userAccount u
				LEFT JOIN Employee e ON e.business.id = b.id
				LEFT JOIN e.userAccount eu
				WHERE u.username = :username OR eu.username = :username
		\s""")
    Business findByUsername(String username);

    /**
     * Tìm business qua id
     */
    @Query("SELECT b FROM Business b WHERE b.id = :id")
    Business getBusinessById(Integer id);

    @Query("select b from Business b WHERE b.userAccount.state = 1 and b.status <> 0")
    List<Business> findAllByStatus(Pageable pageable);
}
