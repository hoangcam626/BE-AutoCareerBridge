package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.dto.response.workshop.WorkshopPortalResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopStateBusiness;
import com.backend.autocarrerbridge.util.enums.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Workshop;
import com.backend.autocarrerbridge.util.enums.State;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkShopRepository extends JpaRepository<Workshop, Integer> {
    Workshop findById(int id);

    // Tìm kiếm trong tiêu đề workshop
    @Query("SELECT ws FROM Workshop ws " +
            "WHERE (:keyword IS NULL " +
            "OR LOWER(ws.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND ws.status <> 0")
    Page<Workshop> getAllWorkShop(@Param("keyword") String keyword, Pageable pageable);

    // Tìm kiếm theo tiêu đề workshop của các trường đại học cụ thể
    @Query("SELECT ws FROM Workshop ws WHERE ws.status != 0 AND"
            + "(:universityId IS NULL OR ws.university.id = :universityId) "
            + "AND (:keyword IS NULL OR LOWER(ws.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) order by  ws.updatedAt DESC")
    Page<Workshop> getAllWorkShopByUniversity(
            Pageable pageable, @Param("universityId") Integer universityId, @Param("keyword") String keyword);


    // Tìm kiếm theo tiêu đề workshop theo địa điểm (không giới hạn bởi trường đại học)
    @Query("SELECT ws FROM Workshop ws LEFT JOIN Location l ON ws.location.id = l.id WHERE l.province.id = :idProvinces")
    Page<Workshop> getAllWorkShopByLocation(Pageable pageable, @Param("idProvinces") Integer idProvinces);


    // Tìm kiếm theo trạng thái duyệt (statusBrowse)
    @Query("SELECT ws FROM Workshop ws WHERE " + "(:approved IS NULL OR ws.statusBrowse = :approved) "
            + "AND (:keyword IS NULL OR LOWER(ws.title) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Workshop> getAllWorkshopByState(
            Pageable pageable, @Param("approved") State approved, @Param("keyword") String keyword);

    @Query("SELECT ws " +
            "FROM Workshop ws " +
            "WHERE ws.statusBrowse = :state " +
            "AND (:keyword IS NULL OR " +
            "   ws.title LIKE :keyword ESCAPE '\\' OR " +
            "   ws.university.name LIKE :keyword ESCAPE '\\') " +
            "AND ws.status <> 0 " +
            "ORDER BY ws.updatedAt DESC ")
    Page<Workshop> findAllByState(Pageable pageable, State state, String keyword);

    @Query("SELECT ws " +
            "FROM Workshop ws " +
            "WHERE (:keyword IS NULL OR " +
            "   ws.title LIKE :keyword ESCAPE '\\' OR " +
            "   ws.university.name LIKE :keyword ESCAPE '\\') " +
            "AND ws.status <> 0 " +
            "ORDER BY ws.updatedAt DESC ")
    Page<Workshop> findAll(Pageable pageable, String keyword);

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.workshop.WorkshopPortalResponse(" +
            "ws.id, " +
            "ws.university.name, " +
            "ws.university.logoImageId, " +
            "ws.title, " +
            "ws.description, " +
            "ws.startDate, " +
            "ws.endDate, " +
            "ws.expireDate, " +
            "ws.workshopImageId, " +
            "ws.location.description, " +
            "ws.location.province.name, " +
            "ws.location.district.fullName, " +
            "ws.location.ward.fullName, " +
            "SUM(CASE WHEN wsb.statusConnected = :approved THEN 1 ELSE 0 END)," +
            "ws.createdAt, " +
            "ws.updatedAt " +
            ") " +
            "FROM Workshop ws " +
            "LEFT JOIN WorkshopBusiness wsb ON ws.id = wsb.workshop.id " +
            "WHERE ws.statusBrowse = :approved " +
            "AND (:provinceId IS NULL OR ws.location.province.id = :provinceId) " +
            "AND (:startDate IS NULL OR ws.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR ws.endDate <= :endDate) " +
            "AND (:universityId IS NULL OR ws.university.id = :universityId)" +
            "AND ws.status =:status " +
            "AND ws.expireDate >= CURRENT_DATE " +
            "AND (:keyword IS NULL OR LOWER(ws.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ESCAPE '\\') " +
            "GROUP BY ws.id, ws.university.name, ws.title, ws.description, ws.startDate, ws.endDate, " +
            "ws.expireDate, ws.workshopImageId, ws.location.description, ws.location.province.name, " +
            "ws.location.district.name, ws.location.ward.name order by ws.expireDate ASC ")
    Page<WorkshopPortalResponse> getAllWorkshopApprovedAndLocation(
            Pageable pageable,
            @Param("approved") State approved,
            @Param("provinceId") Integer provinceId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("universityId") Integer universityId,
            @Param("status") Status status,
            @Param("keyword") String keyword);

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.workshop.WorkshopPortalResponse(" +
            "ws.id, " +
            "ws.university.name, " +
            "ws.university.logoImageId, " +
            "ws.title, " +
            "ws.description, " +
            "ws.startDate, " +
            "ws.endDate, " +
            "ws.expireDate, " +
            "ws.workshopImageId, " +
            "ws.location.description, " +
            "ws.location.province.name, " +
            "ws.location.district.fullName, " +
            "ws.location.ward.fullName, " +
            "SUM(CASE WHEN wsb.statusConnected = :state THEN 1 ELSE 0 END)," +
            "ws.createdAt, " +
            "ws.updatedAt " +
            ") " +
            "FROM Workshop ws " +
            "LEFT JOIN WorkshopBusiness wsb ON ws.id = wsb.workshop.id " +
            "where ws.id =:workShopId " +
            "GROUP BY ws.id, ws.university.name, ws.title, ws.description, ws.startDate, ws.endDate, " +
            "ws.expireDate, ws.workshopImageId, ws.location.description, ws.location.province.name, " +
            "ws.location.district.name, ws.location.ward.name  ")
    WorkshopPortalResponse getWorkShopDetailsById(@Param("workShopId") Integer workShopId, @Param("state") State state);

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.workshop.WorkshopStateBusiness(" +
            "ws.id, " +
            "ws.university.id, " +
            "ws.university.logoImageId," +
            "ws.university.name," +
            "ws.university.email," +
            "ws.university.phone," +
            "ws.university.website," +
            "ws.title, " +
            "ws.description, " +
            "ws.status, " +
            "ws.statusBrowse, " +
            "wsb.statusConnected, " +
            "ws.startDate, " +
            "ws.endDate, " +
            "ws.expireDate, " +
            "ws.workshopImageId, " +
            "ws.location.description, " +
            "ws.location.province.name, " +
            "ws.location.district.fullName, " +
            "ws.location.ward.fullName, " +
            "ws.createdAt, " +
            "ws.updatedAt " +
            ") " +
            "FROM Workshop ws " +
            "LEFT JOIN WorkshopBusiness wsb ON ws.id = wsb.workshop.id " +
            "where wsb.business.id =:businessId  AND (wsb.status =:status) AND (ws.status =:status) AND (wsb.statusConnected = :state OR :state IS NULL) AND (:keyword IS NULL OR LOWER(ws.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ESCAPE '\\')")
    Page<WorkshopStateBusiness> findAllWorkShopByBusinessId(@Param("status") Status status, @Param("businessId") Integer businessId, @Param("state") State state, @Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT business.email " +
            "FROM workshop " +
            "JOIN workshop_business ON workshop.id = workshop_business.workshop_id " +
            "JOIN business ON workshop_business.business_id = business.id " +
            "WHERE workshop.id = :workShopId",
            nativeQuery = true)
    List<String> listEmailJoinWorkShop(@Param("workShopId") Integer workShopId);


    @Query("SELECT count(ws.id) FROM Workshop ws WHERE ws.status <> 0 AND ws.statusBrowse = 1")
    Long countWorkshop();

    @Query("SELECT count(ws.id) FROM Workshop ws WHERE ws.createdAt = :date")
    Long countWorkshopByDate(@Param("date") LocalDate date);

    @Query("SELECT  count(ws.id) from Workshop ws where ws.status =:status and ws.statusBrowse =:state")
    Long countWorkShop(@Param("status") Status status, @Param("state") State state);

    @Query("SELECT COUNT(w) FROM Workshop w WHERE w.university.id = :universityId")
    long countWorkShopByUniversityId(@Param("universityId") Integer universityId);
}
