package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.AdministrativeRegion;
import com.backend.autocarrerbridge.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminstrativeRegionRepository extends JpaRepository<AdministrativeRegion, Integer> {
}
