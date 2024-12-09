package com.backend.autocarrerbridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.Ward;

public interface WardRepository extends JpaRepository<Ward, Integer> {

    List<Ward> findByDistrictId(Integer districtId);
}
