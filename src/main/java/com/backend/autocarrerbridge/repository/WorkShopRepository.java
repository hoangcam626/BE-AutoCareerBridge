package com.backend.autocarrerbridge.repository;

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

    @Query("SELECT ws FROM Workshop ws WHERE ws.status != 0 AND " +
            "(ws.title LIKE %:keyword% OR ws.university.name LIKE %:keyword%)")
    Page<Workshop> getAllWorkShop(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT ws FROM Workshop ws WHERE ws.university.id = :universityId AND " +
            "(ws.title LIKE %:keyword% OR ws.university.name LIKE %:keyword%)")
    Page<Workshop> getAllWorkShopByUniversity(Pageable pageable,
                                              @Param("universityId") Integer universityId,
                                              @Param("keyword") String keyword);

    @Query("SELECT ws FROM Workshop ws JOIN University us ON ws.university.id = us.id WHERE " +
            "(ws.title LIKE %:keyword% OR ws.university.name LIKE %:keyword%)")
    Page<Workshop> getAllWorkShopByLocation(Pageable pageable,
                                            @Param("keyword") String keyword);

    @Query("SELECT ws FROM Workshop ws WHERE ws.statusBrowse = :approved AND " +
            "(ws.title LIKE %:keyword% OR ws.university.name LIKE %:keyword%)")
    Page<Workshop> getAllApprovedWorkshop(Pageable pageable,
                                          @Param("approved") State approved,
                                          @Param("keyword") String keyword);
}
