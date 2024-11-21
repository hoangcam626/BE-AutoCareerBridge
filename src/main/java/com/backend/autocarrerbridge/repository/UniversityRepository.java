package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Integer> {
    University findByPhone(String phone);
    University findByEmail(String email);
}
