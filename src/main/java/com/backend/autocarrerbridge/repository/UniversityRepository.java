package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.University;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UniversityRepository extends JpaRepository<University, Integer> {
    University findByPhone(String phone);

    University findByEmail(String email);
    
}
