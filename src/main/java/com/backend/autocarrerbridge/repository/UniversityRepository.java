package com.backend.autocarrerbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.University;

public interface UniversityRepository extends JpaRepository<University, Integer> {
    University findByPhone(String phone);

    University findByEmail(String email);
}
