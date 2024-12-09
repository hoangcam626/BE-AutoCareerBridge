package com.backend.autocarrerbridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.District;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District> findByProvinceId(Integer provinceId);
}
