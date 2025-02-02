package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.util.enums.Status;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, Integer> {

  List<Major> findByName(String name);

  List<Major> findByCode(String code);

  Optional<Major> findById(int id);

  // Tìm các majors theo sectionId và status
  List<Major> findBySectionIdAndStatus(int sectionId, Status status);

  @Query("SELECT SUM(m.numberStudent) FROM Major m WHERE m.section.university.id =:universityId")
  int getTotalStudent(@Param("universityId") Integer universityId);

  @Query("SELECT m.name, m.numberStudent FROM Major m WHERE m.section.university.id =:universityId")
  List<Object[]> countStudentInMajor(@Param("universityId") Integer universityId);

  @Query("SELECT m FROM Major m WHERE m.section.university.id = :universityId")
  List<Major> findByUniversityIdAndSection(@Param("universityId") Integer universityId);

  @Query("SELECT COUNT(m) FROM Major m WHERE m.section.university.id =:universityId")
  long countMajorByUniversityId(@Param("universityId") Integer universityId);
}
