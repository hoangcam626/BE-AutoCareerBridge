package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.SubAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubAdminRepository extends JpaRepository<SubAdmin, Integer> {

    boolean existsByEmail(String email);

    boolean existsBySubAdminCode(String code);

    @Query("select sa from SubAdmin sa where sa.status <> 0")
    Page<SubAdmin> findAll(Pageable pageable);

    @Query("select sa from SubAdmin sa where sa.status <> 0")
    List<SubAdmin> findAll();
}
