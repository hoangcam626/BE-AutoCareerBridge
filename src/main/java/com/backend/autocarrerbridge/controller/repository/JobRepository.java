package com.backend.autocarrerbridge.controller.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.entity.Job;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    /**
     * Lấy danh sách công việc của doanh nghiệp
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.job.JobResponse(job) "
            + "FROM Job job join job.business business where business.id =:businessId AND job.status = 1" +
            "AND (:keyword IS NULL OR :keyword = '' " +
            "OR job.level like %:keyword% or job.title like %:keyword%)" +
            "ORDER BY job.createdAt DESC ")
    Page<JobResponse> getAllJobOfBusiness(Integer businessId, @RequestParam("keyword")String keyword, Pageable pageable);

    /**
     * Lấy tất cả công việc
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.job.JobResponse(job) "
            + "FROM Job job join job.business business where job.status = 1" +
            "AND (:keyword IS NULL OR :keyword = '' " +
            "OR job.level like %:keyword% or job.title like %:keyword%)" +
            "ORDER BY job.createdAt DESC ")
    Page<JobResponse> getAllJob(@RequestParam("keyword")String keyword, Pageable pageable);

    @Query("SELECT j from Job j where j.id = :jobId")
    Job getJobDetail(Integer jobId);
}
