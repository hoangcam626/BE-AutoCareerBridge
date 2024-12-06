package com.backend.autocarrerbridge.controller.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Workshop;
import com.backend.autocarrerbridge.util.enums.State;

@Repository
public interface WorkShopRepository extends JpaRepository<Workshop, Integer> {

    // Tìm kiếm trong tiêu đề workshop
    @Query("SELECT ws FROM Workshop ws WHERE " +
            "(:keyword IS NULL OR LOWER(ws.title) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Workshop> getAllWorkShop(@Param("keyword") String keyword, Pageable pageable);

    // Tìm kiếm theo tiêu đề workshop của các trường đại học cụ thể
    @Query("SELECT ws FROM Workshop ws WHERE ws.status != 0 AND" +
            "(:universityId IS NULL OR ws.university.id = :universityId) " +
            "AND (:keyword IS NULL OR LOWER(ws.title) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Workshop> getAllWorkShopByUniversity(
            Pageable pageable, @Param("universityId") Integer universityId, @Param("keyword") String keyword);

    // Tìm kiếm theo tiêu đề workshop theo địa điểm (không giới hạn bởi trường đại học)
    @Query("SELECT ws FROM Workshop ws WHERE " +
            "(:keyword IS NULL OR LOWER(ws.title) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Workshop> getAllWorkShopByLocation(Pageable pageable, @Param("keyword") String keyword);

    // Tìm kiếm theo trạng thái duyệt (statusBrowse)
    @Query("SELECT ws FROM Workshop ws WHERE " +
            "(:approved IS NULL OR ws.statusBrowse = :approved) " +
            "AND (:keyword IS NULL OR LOWER(ws.title) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Workshop> getAllWorkshopByState(
            Pageable pageable, @Param("approved") State approved, @Param("keyword") String keyword);
}


