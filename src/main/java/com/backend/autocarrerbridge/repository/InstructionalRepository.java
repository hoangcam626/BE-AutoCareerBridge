package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Instructional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionalRepository extends JpaRepository<Instructional,Integer> {
    Instructional findByName(String name);
}
