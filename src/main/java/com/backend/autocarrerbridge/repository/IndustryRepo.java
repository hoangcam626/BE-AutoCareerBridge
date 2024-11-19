package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.dto.industry.IndustrySdo;
import com.backend.autocarrerbridge.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndustryRepo extends JpaRepository<Industry, Integer> {

    @Query("SELECT new com.backend.autocarrerbridge.dto.industry.IndustrySdo(industry) " +
            "FROM Industry industry WHERE industry.status = com.backend.autocarrerbridge.util.enums.Status.ACTIVE")
    List<IndustrySdo> getAllIndustryActive();
    boolean existsByName(String name);
    boolean existsByCode(String code);
    @Query("SELECT industry FROM Industry industry where industry.id= :id")
    Industry getIndustriesById(Integer id);
}
