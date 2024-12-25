package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.dto.response.business.IntroduceBusiness;
import com.backend.autocarrerbridge.util.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Business;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Integer> {
    Business findByEmail(String email);

    Business findByTaxCode(String taxCode);

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
            "WHERE (:state IS NULL OR b.userAccount.state = :state) " +
            "AND :keyword IS NULL OR " +
            "     LOWER(b.name) LIKE :keyword ESCAPE '\\' " +
            "      OR LOWER(b.email) LIKE :keyword ESCAPE '\\' " +
            "ORDER BY b.updatedAt DESC ")
    Page<Business> findAllByState(Pageable pageable, State state, String keyword);

    /**
     * Tìm kiếm các doanh nghiệp theo tạng thái và từ tìm kiếm
     */
    @Query("SELECT b " +
            "FROM Business b " +
            "WHERE :keyword IS NULL OR " +
            "     LOWER(b.name) LIKE :keyword ESCAPE '\\' " +
            "      OR LOWER(b.email) LIKE :keyword ESCAPE '\\' " +
            "ORDER BY b.updatedAt DESC ")
    Page<Business> findAll(Pageable pageable, String keyword);


    @Query("""
    SELECT new com.backend.autocarrerbridge.dto.response.business.IntroduceBusiness(
        b.id, 
        b.name, 
        bi.industry.id,
        bi.industry.name,
        COUNT(j), 
        b.businessImageId
    )
    FROM Business b
    JOIN BusinessIndustry bi ON b.id = bi.business.id
    LEFT JOIN Job j ON j.business.id = b.id
    WHERE bi.industry.id = :industryId OR :industryId IS NULL
    GROUP BY b.id, b.name, bi.industry.name, b.businessImageId,bi.industry.id
    ORDER BY COUNT(j) DESC
""")
    List<IntroduceBusiness> getBusinessFeaturedByIndustry(@Param("industryId") Integer industryId, Pageable pageable);


}

