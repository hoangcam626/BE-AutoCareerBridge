package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.util.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Business;

@Repository
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
    @Query("SELECT DISTINCT b " +
            "FROM Business b " +
            "LEFT JOIN b.userAccount u " +
            "LEFT JOIN Employee e ON e.business.id = b.id " +
            "LEFT JOIN e.userAccount eu " +
            "WHERE u.username = :username " +
            "OR eu.username = :username")
    Business findByUsername(String username);

    /**
     * Tìm business qua id
     */
    @Query("SELECT b FROM Business b WHERE b.id = :id")
    Business getBusinessById(Integer id);

    /**
     * Tìm kiếm các doanh nghiệp theo tạng thái và từ tìm kiếm
     */
    @Query("SELECT b " +
            "FROM Business b " +
            "WHERE b.userAccount.state = :state " +
            "AND (:keyword IS NULL OR " +
            "     LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "      OR LOWER(b.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND b.status <> 0 " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN LOWER(b.name) = LOWER(:keyword) THEN 1 " +
            "       WHEN LOWER(b.email) = LOWER(:keyword) THEN 1 " +
            "       ELSE 2 " +
            "   END, " +
            "   b.createdAt DESC ")
    Page<Business> findAllByState(Pageable pageable, State state, String keyword);

    /**
     * Tìm kiếm các doanh nghiệp theo tạng thái và từ tìm kiếm
     */
    @Query("SELECT b " +
            "FROM Business b " +
            "WHERE (:keyword IS NULL OR " +
            "     (LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "      OR LOWER(b.email) LIKE LOWER(CONCAT('%', :keyword, '%')))) " +
            "AND b.status <> 0 " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN LOWER(b.name) = LOWER(:keyword) THEN 1 " +
            "       WHEN LOWER(b.email) = LOWER(:keyword) THEN 1 " +
            "       ELSE 2 " +
            "   END, " +
            "   b.createdAt DESC ")
    Page<Business> findAll(Pageable pageable, String keyword);
}
