package com.backend.autocarrerbridge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.SubAdmin;

@Repository
public interface SubAdminRepository extends JpaRepository<SubAdmin, Integer> {

    @Query("SELECT CASE WHEN COUNT(sa) > 0 THEN TRUE ELSE FALSE END "
            + "FROM SubAdmin sa WHERE sa.email = :email AND sa.status <> 0")
    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(sa) > 0 THEN TRUE ELSE FALSE END "
            + "FROM SubAdmin sa WHERE sa.subAdminCode = :code AND sa.status <> 0")
    boolean existsBySubAdminCode(String code);

    @Query("select sa from SubAdmin sa where sa.status <> 0")
    Page<SubAdmin> findAllPageable(Pageable pageable);

    @Query("select sa from SubAdmin sa where sa.status <> 0")
    List<SubAdmin> findAllByStatus();

    SubAdmin findByEmail(String email);
}
