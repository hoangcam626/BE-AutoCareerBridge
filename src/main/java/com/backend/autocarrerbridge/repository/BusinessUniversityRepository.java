package com.backend.autocarrerbridge.repository;

import java.util.List;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select bu.business.email from BusinessUniversity bu where bu.id = :id and bu.status != 0")
    String getEmailBusinessFromIdCooperation(Integer id);

    @Query("SELECT cooperation "
            + "FROM BusinessUniversity cooperation WHERE cooperation.university.email = :email "
            + "AND cooperation.status = com.backend.autocarrerbridge.util.enums.Status.ACTIVE "
            + "AND (:keyword IS NULL OR :keyword = '' "
            + "OR cooperation.business.name like %:keyword% or cooperation.business.email like %:keyword%)"
            + "ORDER BY cooperation.createdAt ASC ")
    Page<BusinessUniversity> getCooperationForPaging(String email, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT cooperation "
            + "FROM BusinessUniversity cooperation WHERE cooperation.university.email = :email "
            + "AND cooperation.statusConnected = com.backend.autocarrerbridge.util.enums.State.PENDING "
            + "AND (:keyword IS NULL OR :keyword = '' "
            + "OR cooperation.business.name like %:keyword% or cooperation.business.email like %:keyword%)"
            + "ORDER BY cooperation.createdAt ASC ")
    Page<BusinessUniversity> getCooperationPendingForPaging(String email, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT cooperation "
            + "FROM BusinessUniversity cooperation WHERE cooperation.university.email = :email "
            + "AND cooperation.statusConnected = com.backend.autocarrerbridge.util.enums.State.APPROVED "
            + "AND (:keyword IS NULL OR :keyword = '' "
            + "OR cooperation.business.name like %:keyword% or cooperation.business.email like %:keyword%)"
            + "ORDER BY cooperation.createdAt ASC ")
    Page<BusinessUniversity> getCooperationApproveForPaging(String email, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT cooperation "
            + "FROM BusinessUniversity cooperation WHERE cooperation.university.email = :email "
            + "AND cooperation.statusConnected = com.backend.autocarrerbridge.util.enums.State.REJECTED "
            + "AND (:keyword IS NULL OR :keyword = '' "
            + "OR cooperation.business.name like %:keyword% or cooperation.business.email like %:keyword%)"
            + "ORDER BY cooperation.createdAt ASC ")
    Page<BusinessUniversity> getCooperationRejectForPaging(String email, @Param("keyword") String keyword, Pageable pageable);

}
