package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    /**
     * Lấy danh sách công việc của doanh nghiệp
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.job.JobResponse(job) "
            + "FROM Job job join job.business business where business.id = :id")
    List<JobResponse> getAllJob(Integer id);

    @Query("SELECT j from Job j where j.id = :jobId")
    Job getJobDetail(Integer jobId);
}
