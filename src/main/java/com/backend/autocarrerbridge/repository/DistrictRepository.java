package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.District;
import com.backend.autocarrerbridge.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DistrictRepository extends JpaRepository<District, Integer> {

}
