package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Integer> {
    University findByName(String name);
    University findByEmail(String email);
    University findByPhone(String phone);
}
