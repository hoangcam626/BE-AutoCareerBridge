package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BussinessRepository extends JpaRepository<Business,Integer> {
    Business findByEmail(String email);
}
