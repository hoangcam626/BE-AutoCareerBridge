package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.University;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {

  University findByName(String name);

  University findByEmail(String email);

  University findByPhone(String phone);
}
