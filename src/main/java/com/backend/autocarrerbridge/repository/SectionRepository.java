package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Section;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

  Section findByName(String name);

  Optional<Section> findById(int id);

}
