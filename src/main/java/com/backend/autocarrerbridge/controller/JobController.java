package com.backend.autocarrerbridge.controller;

import java.text.ParseException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.job.JobRequest;
import com.backend.autocarrerbridge.service.JobService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/job")
public class JobController {

    JobService jobService;

    /**
     * API lấy danh sách tất cả công việc đã được đăng tuyển.
     *
     * @return Danh sách các công việc đã đăng tuyển.
     * @apiNote Sử dụng để truy vấn danh sách công việc trong cơ sở dữ liệu.
     */
    @GetMapping("/get-all-job")
    public ApiResponse<Object> getAllJob(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam String keyword) throws ParseException {
        Pageable pageable = PageRequest.of(page - 1, size);
        return jobService.getAllJob(page, size, keyword, pageable);
    }

    /**
     * API lấy danh sách tất cả công việc đã được đăng tuyển của doanh nghiệp.
     *
     * @return Danh sách các công việc đã đăng tuyển.
     * @apiNote Sử dụng để truy vấn danh sách công việc trong cơ sở dữ liệu.
     */
    @GetMapping("/get-all-job-of-business-paging")
    public ApiResponse<Object> getAllJobOfBusinessPaging(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam String keyword) throws ParseException {
        Pageable pageable = PageRequest.of(page - 1, size);
        return jobService.getAllJobOfBusinessPaging(page, size, keyword, pageable);
    }

    /**
     * API để lấy chi tiết công việc đã đăng tuyển
     *
     * @apiNote để truy vấn chi tiết công việc trong cơ sở dữ liệu
     */
    @GetMapping("/get-detail")
    public ApiResponse<Object> getDetail(@RequestParam Integer id) throws ParseException {
        return jobService.getJobDetail(id);
    }

    /**
     * API để tao công việc
     *
     * @apiNote để để thêm công việc vào cơ sở dữ liệu
     */
    @PostMapping("/create-job")
    public ApiResponse<Object> createJob(@RequestBody JobRequest jobRequest) throws ParseException {
        return jobService.createJob(jobRequest);
    }

    /**
     * API để cập nhật công việc
     *
     * @apiNote để để cập nhật thông tin công việc vào cơ sở dữ liệu
     */
    @PutMapping("/update-job")
    public ApiResponse<Object> updateJob(@RequestParam Integer jobId, @RequestBody JobRequest jobRequest)
            throws ParseException {
        return jobService.updateJob(jobId, jobRequest);
    }

    /**
     * API để vô hiệu hóa công việc
     *
     * @apiNote để để vô hiệu hóa công việc đã đăng
     */
    @PutMapping("/inactive-job")
    public ApiResponse<Object> inactiveJob(@RequestParam Integer jobId) throws ParseException {
        return jobService.inactiveJob(jobId);
    }

    @GetMapping("/business-total-job")
    public ApiResponse<Object> getBusinessTotalJob(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size)  {
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.builder().build().setData(jobService.getBusinessJob(pageable));
    }

    @GetMapping("/region/{regionId}")
    public ApiResponse<Object> getBusinessJobByRegion(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size, @PathVariable("regionId") Integer regionId)  {
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.builder().build().setData(jobService.getJobBusinessByRegion(pageable,regionId));
    }

    @GetMapping("/district/{districtId}")
    public ApiResponse<Object> getBusinessJobByDistrict(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size, @PathVariable("districtId") Integer districtId)  {
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.builder().build().setData(jobService.getJobBusinessByDistrict(pageable,districtId));
    }
    @GetMapping("/province/{provinceId}")
    public ApiResponse<Object> getBusinessJobByProvince(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size, @PathVariable("provinceId") Integer provinceId)  {
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.builder().build().setData(jobService.getJobBusinessByProvince(pageable,provinceId));
    }
    @GetMapping("/job-all")
    public ApiResponse<Object> getBusinessJobAll(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size)  {
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.builder().build().setData(jobService.getAllJobBusiness(pageable));
    }

    @GetMapping("/industry/{industryId}")
    public ApiResponse<Object> getJobByIndustry(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                    @PathVariable("industryId") Integer industryId)  {
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.builder().build().setData(jobService.getAllJobBusinessByIndustry(pageable,industryId));
    }

    @GetMapping("/salary")
    public ApiResponse<Object> getJobByIndustry(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam("minSalary") Long minSalary,
                                                @RequestParam("maxSalary") Long maxSalary)  {
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.builder().build().setData(jobService.getAllJobBusinessBySalary(pageable,minSalary,maxSalary));
    }
    @GetMapping("/total-job")
    public ApiResponse<Object> getTotalJobByIndustry(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "50") int size)  {
        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.builder().build().setData(jobService.getTotalJobByIndustry(pageable));
    }

    @GetMapping("/check-inactive-permission")
    public ApiResponse<Object> checkInactivePermission(@RequestParam Integer jobId) throws ParseException {
        return jobService.checkDeletePermission(jobId);
    }

}
