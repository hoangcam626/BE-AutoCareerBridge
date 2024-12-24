package com.backend.autocarrerbridge.repository;

import java.util.List;

import com.backend.autocarrerbridge.dto.response.industry.IndustryJobCountDTO;
import com.backend.autocarrerbridge.dto.response.industry.IndustrySalaryDTO;
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

    /**
     * Thống kê ngành được sử dụng nhiều nhất của doanh nghiệp
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.industry.IndustryJobCountDTO(bi.name, COUNT(j)) " +
            "FROM Job j " +
            "JOIN j.industry bi " +
            "WHERE j.business.id = :businessId AND j.status = com.backend.autocarrerbridge.util.enums.Status.ACTIVE " +
            "GROUP BY bi.name " +
            "ORDER BY COUNT(j) DESC")
    List<IndustryJobCountDTO> findMostUsedIndustryByBusiness(Integer businessId);

    /**
     * Thống kê mức lương trung bình của từng ngành nghề
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.industry.IndustrySalaryDTO(i.name, AVG(j.salary)) " +
            "FROM Job j " +
            "JOIN j.industry i " +
            "WHERE j.salary IS NOT NULL " +
            "GROUP BY i.name " +
            "ORDER BY AVG(j.salary) DESC")
    List<IndustrySalaryDTO> getAverageSalaryByIndustry(Integer id);


}
