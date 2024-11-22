package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Major;
import com.backend.autocarrerbridge.entity.Section;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major, Integer> {

  Major findByName(String name);

  Optional<Major> findById(int id);

}
