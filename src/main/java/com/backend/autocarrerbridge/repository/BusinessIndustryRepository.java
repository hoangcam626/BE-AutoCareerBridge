package com.backend.autocarrerbridge.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.dto.response.industry.BusinessIndustryDto;
import com.backend.autocarrerbridge.entity.BusinessIndustry;

@Repository
public interface BusinessIndustryRepository extends JpaRepository<BusinessIndustry, Integer> {
    BusinessIndustry findByBusinessIdAndIndustryId(Integer businessId, Integer industryId);

    /**
     * Lấy ra danh sách ngành nghề của Business
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.industry.BusinessIndustryDto (bi) "
            + "FROM BusinessIndustry bi WHERE bi.business.id = :id")
    Page<BusinessIndustryDto> getIndustryOfBusiness(Integer id, Pageable pageable);
}
