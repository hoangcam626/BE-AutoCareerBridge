package com.backend.autocarrerbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {}
