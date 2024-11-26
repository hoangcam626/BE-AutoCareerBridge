package com.backend.autocarrerbridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.job.JobResponse(job) " + "FROM Job job")
    List<JobResponse> getAllJob();
}
