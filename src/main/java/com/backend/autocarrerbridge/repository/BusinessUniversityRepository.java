package com.backend.autocarrerbridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.BusinessUniversity;

@Repository
public interface BusinessUniversityRepository extends JpaRepository<BusinessUniversity, Integer> {
    BusinessUniversity findByBusinessIdAndUniversityId(Integer businessId, Integer universityId);

    @Query("SELECT bu FROM BusinessUniversity bu where bu.business.id = :id")
    List<BusinessUniversity> getSentRequestOfBusiness(Integer id);
}
