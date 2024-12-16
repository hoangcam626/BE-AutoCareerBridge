package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Major;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    Section findByName(String name);

    Optional<Section> findById(int id);

}
