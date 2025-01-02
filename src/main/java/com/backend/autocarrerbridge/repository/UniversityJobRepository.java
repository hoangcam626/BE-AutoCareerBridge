package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.UniversityJob;
import com.backend.autocarrerbridge.util.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UniversityJobRepository extends JpaRepository<UniversityJob, Integer> {

    @Query("select uj.university from UniversityJob uj "
            + "where uj.job.id = :jobId and uj.statusConnected = :state "
            + "ORDER BY uj.updatedAt DESC ")
    List<University> getAllUniversity(
            Integer jobId, State state);

    @Query("select uj from UniversityJob uj "
            + "where uj.university.id = :universityId "
            + "and (:state IS NULL OR uj.statusConnected = :state) "
            + "AND (:keyword IS NULL OR LOWER(uj.job.title) LIKE :keyword ESCAPE'\\') "
            + "ORDER BY uj.updatedAt DESC ")
    Page<UniversityJob> getAllJobs(
            Integer universityId, State state, String keyword, Pageable pageable);

    @Query("select CASE WHEN COUNT(uj) > 0 THEN TRUE ELSE FALSE END   " +
            "from UniversityJob uj " +
            "where uj.job.id =:jobId and uj.university.id =:universityId " +
            "and uj.status = 1")
    boolean checkExistUniversityJob(
            Integer universityId, Integer jobId);

    @Query("select uj from UniversityJob uj " +
            "where uj.job.id =:jobId and uj.university.id =:universityId " +
            "and uj.status = 1")
    UniversityJob findtUniversityJob(
            Integer universityId, Integer jobId);

    @Query("SELECT count(uj.id) FROM UniversityJob uj WHERE uj.status = 1 AND uj.statusConnected = 1")
    Long countUniversityJob();

    @Query("SELECT count(uj) FROM UniversityJob uj WHERE uj.createdAt between :date and :nextDate")
    Long countUniversityJobByDate(LocalDateTime date, LocalDateTime nextDate);
}
