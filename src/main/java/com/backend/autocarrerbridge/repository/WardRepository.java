package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardRepository extends JpaRepository<Ward, Integer> {
}
