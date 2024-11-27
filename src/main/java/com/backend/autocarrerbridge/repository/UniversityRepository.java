package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.University;
import org.springframework.data.jpa.repository.Query;

public interface UniversityRepository extends JpaRepository<University, Integer> {
    University findByPhone(String phone);

    University findByEmail(String email);

    /**
     * Tìm University qua id
     */
    @Query("SELECT u FROM University u WHERE u.id = :id")
    University getUniversityById(Integer id);
}
