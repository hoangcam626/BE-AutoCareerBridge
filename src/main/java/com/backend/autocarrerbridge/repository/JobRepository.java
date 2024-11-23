package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.dto.job.JobSdo;
import com.backend.autocarrerbridge.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    @Query("SELECT new com.backend.autocarrerbridge.dto.job.JobSdo(job) "
                  + "FROM Job job")
    List<JobSdo> getAllJob();

}
