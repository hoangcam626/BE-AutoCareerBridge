package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.dto.response.industry.BusinessIndustryDto;
import com.backend.autocarrerbridge.entity.BusinessIndustry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessIndustryRepository extends JpaRepository<BusinessIndustry, Integer> {
    BusinessIndustry findByBusinessIdAndIndustryId(Integer businessId, Integer industryId);

    @Query("SELECT CASE WHEN COUNT(bi) > 0 THEN true ELSE false END " +
            "FROM BusinessIndustry bi WHERE bi.business.id = :businessId " +
            "AND bi.industry.id = :industryId")
    boolean existsByIndustry_IdAndBusiness_Id(Integer businessId, Integer industryId);

    @Query("SELECT b FROM BusinessIndustry b WHERE b.id IN :ids")
    List<BusinessIndustry> findByIdIn(List<Integer> ids);

    /**
     * Lấy ra danh sách ngành nghề của Business
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.industry.BusinessIndustryDto (bi) "
            + "FROM BusinessIndustry bi WHERE bi.business.id = :id "
            + "AND bi.industry.status = com.backend.autocarrerbridge.util.enums.Status.ACTIVE "
            + "AND (:keyword IS NULL OR :keyword = '' " +
            "OR bi.industry.name like %:keyword% or bi.industry.code like %:keyword%)" +
            "ORDER BY bi.createdAt DESC ")
    Page<BusinessIndustryDto> getIndustryOfBusiness(Integer id, @Param("keyword") String keyword, Pageable pageable);

    /**
     * Lấy ra danh sách ngành nghề của Business
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.industry.BusinessIndustryDto (bi) "
            + "FROM BusinessIndustry bi WHERE bi.business.id = :id "
            + "ORDER BY bi.createdAt DESC ")
    List<BusinessIndustryDto> getIndustryOfBusinessNoPag(Integer id);
}
