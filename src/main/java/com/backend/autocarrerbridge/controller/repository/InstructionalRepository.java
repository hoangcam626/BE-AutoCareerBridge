package com.backend.autocarrerbridge.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Instructional;

@Repository
public interface InstructionalRepository extends JpaRepository<Instructional, Integer> {
    Instructional findByName(String name);
}
