package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Instructional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionalRepository extends JpaRepository<Instructional, Integer> {

  Instructional findByInstructionalCode(String instructionalCode);

  @Query("SELECT i FROM Instructional i WHERE i.university.id = :universityId")
  Page<Instructional> findAllByUniversityId(@Param("universityId") Integer universityId, Pageable pageable);

  @Query("SELECT i FROM Instructional i WHERE  i.status =com.backend.autocarrerbridge.util.enums.Status.ACTIVE AND i.university.id = :universityId")
  Page<Instructional> findAllActive(@Param("universityId") Integer universityId, Pageable pageable);

  @Query("SELECT i FROM Instructional i WHERE i.status =com.backend.autocarrerbridge.util.enums.Status.INACTIVE AND i.university.id = :universityId")
  Page<Instructional> findAllInactive(@Param("universityId") Integer universityId,Pageable pageable);

  // Thêm phương thức để đếm số lượng Instructional theo universityId
  @Query("SELECT COUNT(i) FROM Instructional i WHERE i.university.id = :universityId")
  long countInstructionalByUniversityId(@Param("universityId") Integer universityId);
}
