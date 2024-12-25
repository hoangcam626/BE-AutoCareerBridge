package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.dto.response.workshop.WorkShopPortalResponse;
import com.backend.autocarrerbridge.util.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Workshop;
import com.backend.autocarrerbridge.util.enums.State;

import java.time.LocalDateTime;

@Repository
public interface WorkShopRepository extends JpaRepository<Workshop, Integer> {

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

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.workshop.WorkShopPortalResponse(" +
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
    Page<WorkShopPortalResponse> getAllWorkshopApprovedAndLocation(
            Pageable pageable,
            @Param("approved") State approved,
            @Param("provinceId") Integer provinceId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("universityId") Integer universityId,
            @Param("status")Status status,
            @Param("keyword") String keyword);

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.workshop.WorkShopPortalResponse(" +
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
    WorkShopPortalResponse getWorkShopDetailsById(@Param("workShopId") Integer workShopId,@Param("state") State state);



}
