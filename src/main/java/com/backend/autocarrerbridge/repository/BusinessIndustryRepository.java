package com.backend.autocarrerbridge.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.dto.response.industry.BusinessIndustryDto;
import com.backend.autocarrerbridge.entity.BusinessIndustry;

@Repository
public interface BusinessIndustryRepository extends JpaRepository<BusinessIndustry, Integer> {
    BusinessIndustry findByBusinessIdAndIndustryId(Integer businessId, Integer industryId);

    @Query("SELECT bi FROM BusinessIndustry bi where bi.id = :id")
    BusinessIndustry getByBusinessIndustryId(Integer id);
    /**
     * Lấy ra danh sách ngành nghề của Business
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.industry.BusinessIndustryDto (bi) "
            + "FROM BusinessIndustry bi WHERE bi.business.id = :id "
            + "AND bi.industry.status = com.backend.autocarrerbridge.util.enums.Status.ACTIVE "
            + "AND (:keyword IS NULL OR :keyword = '' "
            + "OR bi.industry.name like %:keyword% or bi.industry.code like %:keyword%)"
            + "ORDER BY bi.createdAt DESC ")
    Page<BusinessIndustryDto> getIndustryOfBusiness(Integer id, @Param("keyword") String keyword, Pageable pageable);
}
