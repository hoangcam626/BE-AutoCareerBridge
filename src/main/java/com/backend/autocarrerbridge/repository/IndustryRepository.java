package com.backend.autocarrerbridge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.dto.response.industry.IndustryResponse;
import com.backend.autocarrerbridge.entity.Industry;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Integer> {

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.industry.IndustryResponse(industry) "
            + "FROM Industry industry WHERE industry.status = com.backend.autocarrerbridge.util.enums.Status.ACTIVE")
    List<IndustryResponse> getAllIndustryActive();

    /**
     * Phân trang danh sách các ngành
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.industry.IndustryResponse(industry) "
            + "FROM Industry industry WHERE industry.status = com.backend.autocarrerbridge.util.enums.Status.ACTIVE "
            + "AND (:keyword IS NULL OR :keyword = ''OR industry.name like %:keyword% or industry.code like %:keyword%) " +
            "ORDER BY industry.createdAt DESC")
    Page<IndustryResponse> getAllIndustryActivePag(@Param("keyword") String keyword, Pageable pageable);

    /**
     * Kiểm tra tên ngành đã tồn tại?
     */
    boolean existsByNameAndIdNot(String name, Integer id);

    boolean existsByName(String name);

    boolean existsByCode(String code);

    /**
     * Kiểm tra mã ngành đã tồn tại?
     */
    boolean existsByCodeAndIdNot(String code, Integer id);

    @Query("SELECT industry FROM Industry industry where industry.id= :id")
    Industry getIndustriesById(Integer id);
}
