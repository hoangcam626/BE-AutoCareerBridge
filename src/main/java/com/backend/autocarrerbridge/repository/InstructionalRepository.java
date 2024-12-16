package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Instructional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionalRepository extends JpaRepository<Instructional, Integer> {

  Instructional findByInstructionalCode(String instructionalCode);

  @Query("SELECT i FROM Instructional i WHERE  i.status =com.backend.autocarrerbridge.util.enums.Status.ACTIVE")
  Page<Instructional> findAllActive(Pageable pageable);

  @Query("SELECT i FROM Instructional i WHERE i.status =com.backend.autocarrerbridge.util.enums.Status.INACTIVE")
  Page<Instructional> findAllInactive(Pageable pageable);
}
