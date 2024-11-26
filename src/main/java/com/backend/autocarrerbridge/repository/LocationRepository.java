package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
