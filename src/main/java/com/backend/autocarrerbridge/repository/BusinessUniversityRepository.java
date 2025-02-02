package com.backend.autocarrerbridge.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.autocarrerbridge.util.enums.State;
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

    @Query("SELECT bu FROM BusinessUniversity bu where bu.business.id = :id "
            + "AND bu.status = com.backend.autocarrerbridge.util.enums.Status.ACTIVE "
            + "AND (:keyword IS NULL OR :keyword = '' "
            + "OR bu.business.name like :keyword ESCAPE '\\' or bu.business.email like  :keyword ESCAPE '\\' )"
            + "AND (:statusConnected IS NULL OR bu.statusConnected = :statusConnected) "
            + "ORDER BY bu.createdAt DESC ")
    Page<BusinessUniversity> getSentRequestOfBusiness(Integer id,
                                                      @Param("keyword") String keyword,
                                                      @Param("status") State statusConnected,
                                                      Pageable pageable);

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
            + "OR cooperation.business.name LIKE :keyword ESCAPE '\\' or cooperation.business.email LIKE :keyword ESCAPE '\\')"
            + "AND (:statusConnected IS NULL OR cooperation.statusConnected = :statusConnected) "
            + "ORDER BY cooperation.createdAt DESC ")
    Page<BusinessUniversity> getCooperationForPaging(String email,
                                                     @Param("keyword") String keyword,
                                                     @Param("status") State statusConnected,
                                                     Pageable pageable
    );

    @Query("SELECT COUNT(w) FROM BusinessUniversity w WHERE w.university.id = :universityId")
    long countBussinessUniversityId(@org.springframework.data.repository.query.Param("universityId") Integer universityId);
    @Query("SELECT count(bu.id) FROM BusinessUniversity bu WHERE bu.status <> 0 AND bu.statusConnected = 1")
    Long countBusinessUniversity ();

    @Query("SELECT count(bu.id) FROM BusinessUniversity bu WHERE bu.createdAt between :date and :nextDate")
    Long countBusinessUniversityByDate(LocalDateTime date, LocalDateTime nextDate);

}
