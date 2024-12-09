package com.backend.autocarrerbridge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    Section findByName(String name);

    Optional<Section> findById(int id);
}
