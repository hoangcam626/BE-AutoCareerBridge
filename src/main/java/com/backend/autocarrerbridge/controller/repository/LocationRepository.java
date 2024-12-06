package com.backend.autocarrerbridge.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {}
