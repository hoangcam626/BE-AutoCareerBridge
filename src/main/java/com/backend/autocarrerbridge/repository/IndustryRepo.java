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
public interface IndustryRepo extends JpaRepository<Industry, Integer> {

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.industry.IndustryResponse(industry) "
            + "FROM Industry industry WHERE industry.status = com.backend.autocarrerbridge.util.enums.Status.ACTIVE")
    List<IndustryResponse> getAllIndustryActive();

    // Phan trang danh sach nganh
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.industry.IndustryResponse(industry) "
            + "FROM Industry industry WHERE industry.status = com.backend.autocarrerbridge.util.enums.Status.ACTIVE")
    Page<IndustryResponse> getAllIndustryActivePag(
            @Param("name") String name, @Param("code") String code, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByCode(String code);

    @Query("SELECT industry FROM Industry industry where industry.id= :id")
    Industry getIndustriesById(Integer id);
}
