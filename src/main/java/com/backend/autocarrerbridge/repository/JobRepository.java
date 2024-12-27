package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.dto.response.industry.JobIndustryResponse;
import com.backend.autocarrerbridge.dto.response.job.AdminJobResponse;
import com.backend.autocarrerbridge.dto.response.job.BusinessJobResponse;
import com.backend.autocarrerbridge.dto.response.job.BusinessTotalResponse;
import com.backend.autocarrerbridge.entity.Industry;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.entity.Job;
import com.backend.autocarrerbridge.util.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    /**
     * Lấy danh sách công việc paging
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.job.JobResponse(job) "
            + "FROM Job job join job.business business where business.id =:businessId "
            + "AND job.status = 1"
            + "AND (:keyword IS NULL OR :keyword = '' "
            + "OR job.level like :keyword ESCAPE '\\' or job.title like :keyword ESCAPE '\\')"
            + "AND (:statusBrowse IS NULL OR job.statusBrowse = :statusBrowse) "
            + "AND (:industryId IS NULL OR job.industry.id = :industryId) "
            + "ORDER BY job.createdAt DESC ")
    Page<JobResponse> getAllJobOfBusinessPaging(Integer businessId,
                                                @Param("keyword") String keyword,
                                                @Param("statusBrowse") State statusBrowse,
                                                @Param("industryId") Integer  industryId,
                                                Pageable pageable);

    /**
     * Lấy tất cả công việc của doanh nghiệp
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.job.JobResponse(job) "
            + "FROM Job job where job.status = 1"
            + "AND (:keyword IS NULL OR :keyword = '' "
            + "OR job.level like %:keyword% or job.title like %:keyword% ESCAPE '\\')"
            + "ORDER BY job.createdAt DESC ")
    Page<JobResponse> getAllJob(@RequestParam("keyword") String keyword, Pageable pageable);


    @Query("SELECT j from Job j where j.id = :jobId")
    Job getJobDetail(Integer jobId);

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.job.AdminJobResponse(j.id, j.title, j.business.name, j.createdAt, j.industry.name, j.statusBrowse, j.expireDate) " +
            "FROM Job j " +
            "WHERE j.statusBrowse = :state  " +
            "AND (:keyword IS NULL OR " +
            "   (LOWER(j.title) LIKE :keyword ESCAPE '\\' " +
            "   OR (LOWER(j.business.name) LIKE :keyword ESCAPE '\\'))) " +
            "AND j.status <> 0 " +
            "ORDER BY j.updatedAt DESC ")
    Page<AdminJobResponse> findAllByState(Pageable pageable, State state, String keyword);

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.job.AdminJobResponse(j.id, j.title, j.business.name, j.createdAt, j.industry.name, j.statusBrowse, j.expireDate) " +
            "FROM Job j " +
            "WHERE (:keyword IS NULL OR " +
            "   (LOWER(j.title) LIKE :keyword ESCAPE '\\' " +
            "   OR (LOWER(j.business.name) LIKE :keyword ESCAPE '\\'))) " +
            "AND j.status <> 0 " +
            "ORDER BY j.updatedAt DESC ")
    Page<AdminJobResponse> findAll(Pageable pageable, String keyword);

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.job.BusinessTotalResponse(b.id, b.name, count(j)) " +
            "FROM Job j JOIN Business b ON j.business.id = b.id " +
            "GROUP BY b.id, b.name")
    Page<BusinessTotalResponse> findAllBusinessWithTotalJob(Pageable pageable);

    @Query("""
    SELECT new com.backend.autocarrerbridge.dto.response.job.BusinessJobResponse(
         j.business.name,\s
        j.id,\s
        j.title,\s
        j.expireDate,\s
        j.level,\s
        j.fromSalary,\s
        j.toSalary,\s
        j.jobDescription,
        j.workingTime,\s
         j.requirement,
           j.benefit,
        j.statusBrowse,\s
        j.status,\s
        j.business.businessImageId,
        l.description,\s
        l.province.fullName,\s
        l.district.fullName,\s
        l.ward.fullName,\s
        j.createdAt,\s
        j.createdBy,\s
        j.updatedAt,\s
        j.updatedBy
    )\s
    FROM Job j\s
    JOIN FETCH Business b ON j.business.id = b.id\s
    JOIN FETCH Location l ON b.location.id = l.id\s
    WHERE l.district.id =:districtId\s
""")
    Page<BusinessJobResponse> findJobByDistrict(Pageable pageable, @Param("districtId") Integer districtId);


    @Query("""
    SELECT new com.backend.autocarrerbridge.dto.response.job.BusinessJobResponse(
        j.business.name,\s
        j.id,\s
        j.title,\s
        j.expireDate,\s
        j.level,\s
        j.fromSalary,\s
        j.toSalary,\s
         j.jobDescription,
        j.workingTime,\s
         j.requirement,
           j.benefit,
        j.statusBrowse,\s
        j.status,\s
        j.business.businessImageId,
        l.description,\s
        l.province.fullName,\s
        l.district.fullName,\s
        l.ward.fullName,\s
        j.createdAt,\s
        j.createdBy,\s
        j.updatedAt,\s
        j.updatedBy
    )\s
    FROM Job j\s
    JOIN FETCH Business b ON j.business.id = b.id\s
    JOIN FETCH Location l ON b.location.id = l.id\s
    WHERE l.province.id = :provinceId\s
""")
    Page<BusinessJobResponse> findJobByProvince(Pageable pageable, @Param("provinceId") Integer provinceId);


    @Query("""
    SELECT new com.backend.autocarrerbridge.dto.response.job.BusinessJobResponse(
         j.business.name,\s
        j.id,\s
        j.title,\s
        j.expireDate,\s
        j.level,\s
        j.fromSalary,\s
        j.toSalary,\s
         j.jobDescription,
        j.workingTime,\s
        j.requirement,
          j.benefit,
        j.statusBrowse,\s
        j.status,\s
        j.business.businessImageId,
        l.description,\s
        l.province.fullName,\s
        l.district.fullName,\s
        l.ward.fullName,\s
        j.createdAt,\s
        j.createdBy,\s
        j.updatedAt,\s
        j.updatedBy
    )\s
    FROM Job j\s
    JOIN FETCH Business b ON j.business.id = b.id\s
    JOIN FETCH Location l ON b.location.id = l.id\s
    WHERE l.province.administrativeRegion.id = :regionId
    ORDER BY j.expireDate DESC
""")
    Page<BusinessJobResponse> findJobByRegion(Pageable pageable, @Param("regionId") Integer regionId);

    @Query("""
    SELECT new com.backend.autocarrerbridge.dto.response.job.BusinessJobResponse(
         j.business.name,\s
        j.id,\s
        j.title,\s
        j.expireDate,\s
        j.level,\s
        j.fromSalary,\s
        j.toSalary,\s
         j.jobDescription,
        j.workingTime,\s
          j.requirement,
            j.benefit,
        j.statusBrowse,\s
        j.status,\s
        j.business.businessImageId,
        l.description,\s
        l.province.fullName,\s
        l.district.fullName,\s
        l.ward.fullName,\s
        j.createdAt,\s
        j.createdBy,\s
        j.updatedAt,\s
        j.updatedBy
    )\s
    FROM Job j\s
    JOIN FETCH Business b ON j.business.id = b.id\s
    JOIN FETCH Location l ON b.location.id = l.id\s
    ORDER BY j.expireDate DESC
""")
    Page<BusinessJobResponse> findAllJobBusiness(Pageable pageable);

    @Query("""
    SELECT new com.backend.autocarrerbridge.dto.response.job.BusinessJobResponse(
         j.business.name,\s
        j.id,\s
        j.title,\s
        j.expireDate,\s
        j.level,\s
        j.fromSalary,\s
        j.toSalary,\s
         j.jobDescription,
        j.workingTime,\s
          j.requirement,
            j.benefit,
        j.statusBrowse,\s
        j.status,\s
        j.business.businessImageId,
        l.description,\s
        l.province.fullName,\s
        l.district.fullName,\s
        l.ward.fullName,\s
        j.createdAt,\s
        j.createdBy,\s
        j.updatedAt,\s
        j.updatedBy
    )\s
    FROM Job j\s
    JOIN FETCH Business b ON j.business.id = b.id\s
    JOIN FETCH Location l ON b.location.id = l.id\s
    where j.industry.id =:industryId
    ORDER BY j.expireDate DESC
""")
    Page<BusinessJobResponse> findJobByIndustry(Pageable pageable,@Param("industryId") Integer industryId);

    @Query("""
    SELECT new com.backend.autocarrerbridge.dto.response.job.BusinessJobResponse(
        j.business.name,
        j.id,
        j.title,
        j.expireDate,
        j.level,
        j.fromSalary,\s
        j.toSalary,\s
         j.jobDescription,
        j.workingTime,
        j.requirement,
        j.benefit,
        j.statusBrowse,
        j.status,
        j.business.businessImageId,
        l.description,
        l.province.fullName,
        l.district.fullName,
        l.ward.fullName,
        j.createdAt,
        j.createdBy,
        j.updatedAt,
        j.updatedBy
    )
    FROM Job j
    JOIN FETCH Business b ON j.business.id = b.id
    JOIN FETCH Location l ON b.location.id = l.id
    WHERE (:minSalary IS NULL OR j.fromSalary >= :minSalary)
    AND (:maxSalary IS NULL OR j.toSalary <= :maxSalary)
    ORDER BY j.expireDate DESC
""")
    Page<BusinessJobResponse> findJobBySalary(Pageable pageable,
                                              @Param("minSalary") Long minSalary,
                                              @Param("maxSalary") Long maxSalary);

    /**
     * Tìm job qua ngành nghề
     */
    @Query("SELECT job FROM Job job WHERE job.industry = :industry AND job.status = 1")
    List<Job> getJobByIndustry(Industry industry);

//    @Query("""
//    SELECT new com.backend.autocarrerbridge.dto.response.job.BusinessJobResponse(
//         j.business.name,
//        j.id,
//        j.title,
//        j.expireDate,
//        j.level,
//        j.salary,
//        j.workingTime,
//          j.requirement,
//        j.statusBrowse,
//        j.status,
//        j.business.businessImageId,
//        l.description,
//        l.province.fullName,
//        l.district.fullName,
//        l.ward.fullName,
//        j.createdAt,
//        j.createdBy,
//        j.updatedAt,
//        j.updatedBy
//    )
//    FROM Job j
//    JOIN FETCH Business b ON j.business.id = b.id
//    JOIN FETCH Location l ON b.location.id = l.id
//    where j.
//    ORDER BY j.expireDate DESC
//""")
//    Page<BusinessJobResponse> findJobByExperience(Pageable pageable,Integer start,Integer end);

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.industry.JobIndustryResponse(i.id, i.name, count(jb.id)) " +
            "FROM Industry i LEFT JOIN Job jb ON i.id = jb.industry.id GROUP BY i.id, i.name ")
    Page<JobIndustryResponse> findTotalJobByIndustry(Pageable pageable);

    @Query("SELECT COUNT(j) FROM Job j WHERE j.business.id = :businessId AND j.expireDate BETWEEN :startDate AND :endDate")
    long countJobsByBusinessAndDateRange(
            @Param("businessId") Integer businessId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT COUNT(j.id) from Job j where j.statusBrowse =:state and j.status =:status")
    Long countJobs(@Param("state") State state,@Param("status") Status status);

    @Query("SELECT count(j.id) FROM Job j WHERE j.status <> 0 AND j.statusBrowse = 1")
    Long countJob ();

    @Query("SELECT count(j.id) FROM Job j WHERE j.createdAt = :date")
    Long countJobByDate(@Param("date") LocalDate date);
}
