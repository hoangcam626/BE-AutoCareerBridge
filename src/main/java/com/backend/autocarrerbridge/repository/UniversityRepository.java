package com.backend.autocarrerbridge.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.University;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface UniversityRepository extends JpaRepository<University, Integer> {
    University findByPhone(String phone);

    University findByEmail(String email);

    /**
     * Tìm University qua id
     */
    @Query("SELECT u FROM University u WHERE u.id = :id")
    University getUniversityById(Integer id);

    @Query("SELECT u FROM University u WHERE (:address IS NULL OR u.location.province.name = :address) AND (:name IS NULL OR u.name = :name)")
    List<University> findUniversity(@Param("address") String address, @Param("name") String name);



}
