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
    @Query("SELECT ws from Workshop ws where ws.status != 0")
    Page<Workshop> getAllWorkShop(Pageable pageable);

    @Query("SELECT ws from Workshop ws where ws.university.id =:universityId")
    Page<Workshop> getAllWorkShopByUniversity(Pageable pageable, @Param("universityId") Integer universityId);

    @Query("SELECT ws from Workshop ws join University us on ws.university.id = us.id")
    Page<Workshop> getAllWorkShopByLocation(Pageable pageable);

    @Query("SELECT ws from Workshop ws where ws.statusBrowse = :approved")
    Page<Workshop> getAllApprovedWorkshop(Pageable pageable, @Param("approved") State approved);
}
