package com.backend.autocarrerbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.Business;

public interface BusinessRepository extends JpaRepository<Business, Integer> {
    Business findByEmail(String email);
}
