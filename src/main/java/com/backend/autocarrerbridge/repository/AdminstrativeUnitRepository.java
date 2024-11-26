package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.AdministrativeUnit;
import com.backend.autocarrerbridge.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminstrativeUnitRepository extends JpaRepository<AdministrativeUnit, Integer> {
}
