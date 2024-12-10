package com.backend.autocarrerbridge.repository;

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
    @Query("SELECT e FROM Business e JOIN e.userAccount u WHERE u.username = :username")
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
            "     (LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "      OR LOWER(b.email) LIKE LOWER(CONCAT('%', :keyword, '%')))) " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN LOWER(b.name) = LOWER(:keyword) THEN 1 " +
            "       WHEN LOWER(b.email) = LOWER(:keyword) THEN 1 " +
            "       WHEN LOWER(CONCAT(b.name, ' ', b.email)) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 2 " +
            "       ELSE 3 " +
            "   END")
    Page<Business> findAllByState(Pageable pageable, Integer state, String keyword);
}
