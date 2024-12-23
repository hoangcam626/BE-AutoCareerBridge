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

    @Query("SELECT CASE WHEN COUNT(sa) > 0 THEN TRUE ELSE FALSE END " +
            "FROM SubAdmin sa WHERE sa.email = :email AND sa.status <> 0")
    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(sa) > 0 THEN TRUE ELSE FALSE END " +
            "FROM SubAdmin sa WHERE sa.subAdminCode = :code AND sa.status <> 0")
    boolean existsBySubAdminCode(String code);

    @Query(value = "SELECT sa " +
            "FROM SubAdmin sa " +
            "WHERE (:keyword IS NULL OR " +
            "       LOWER(sa.name) LIKE :keyword ESCAPE '\\' OR " +
            "       LOWER(sa.email) LIKE :keyword ESCAPE '\\' OR " +
            "       LOWER(sa.subAdminCode) LIKE :keyword ESCAPE '\\')" +
            "AND sa.status <> 0 " +
            "ORDER BY sa.updatedAt DESC ")
    Page<SubAdmin> findAllPageable(Pageable pageable, String keyword);

    @Query("select sa from SubAdmin sa where sa.status <> 0")
    List<SubAdmin> findAllByStatus();

    @Query("select sa from SubAdmin sa where sa.email = :email AND sa.status <> 0")
    SubAdmin findByEmail(String email);
}
