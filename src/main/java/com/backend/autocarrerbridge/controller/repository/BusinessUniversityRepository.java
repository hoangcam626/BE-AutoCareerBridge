package com.backend.autocarrerbridge.controller.repository;

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

    @Query("SELECT bu FROM BusinessUniversity bu where bu.university.id = :id")
    List<BusinessUniversity> getAllRequestOfUniversity(Integer id);

    @Query(
            "SELECT bu FROM BusinessUniversity bu where bu.university.id = :id and bu.statusConnected = 0 and bu.status = 1 ")
    List<BusinessUniversity> getBusinessUniversityPending(Integer id);

    @Query(
            "SELECT bu FROM BusinessUniversity bu where bu.university.id = :id and bu.statusConnected = 1 and bu.status = 1 ")
    List<BusinessUniversity> getBusinessUniversityApprove(Integer id);

    @Query(
            "SELECT bu FROM BusinessUniversity bu where bu.university.id = :id and bu.statusConnected = 2 and bu.status = 1 ")
    List<BusinessUniversity> getBusinessUniversityReject(Integer id);

    BusinessUniversity findBusinessUniversityById(Integer id);
}
