package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.converter.ConvertJob;
import com.backend.autocarrerbridge.dto.request.job.JobRequest;
import com.backend.autocarrerbridge.dto.response.job.JobDetailResponse;
import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Employee;
import com.backend.autocarrerbridge.entity.Industry;
import com.backend.autocarrerbridge.entity.Job;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.BusinessRepository;
import com.backend.autocarrerbridge.repository.EmployeeRepository;
import com.backend.autocarrerbridge.repository.IndustryRepo;
import com.backend.autocarrerbridge.repository.JobRepository;
import com.backend.autocarrerbridge.repository.UserAccountRepository;
import com.backend.autocarrerbridge.service.JobService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_CODE_NOT_FOUND;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_EXIST_INDUSTRY;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_EDIT_JOB;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_EXIST_JOB;
import static com.backend.autocarrerbridge.util.Constant.INACTIVE_JOB;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final TokenService tokenService;
    private final UserAccountRepository userAccountRepository;
    private final IndustryRepo industryRepo;
    private final BusinessRepository businessRepository;
    private final EmployeeRepository employeeRepository;
    private final ConvertJob convertJob;

    /**
     * Lấy Employee từ token
     */
    public Employee getEmployeeViaToken() throws ParseException {
        // Cắt chuỗi token
        String token = tokenService.getJWT();
        // Lấy username từ token
        String usernameToken = tokenService.getClaim(token, "sub");
        Employee employeeToken = employeeRepository.findByUsername(usernameToken);
        if (employeeToken == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        return employeeToken;
    }

    /**
     * Lấy username của employee qua token
     */
    public String getUsernameViaToken() throws ParseException {
        String token = tokenService.getJWT();
        // Lấy username từ token
        return tokenService.getClaim(token, "sub");
    }

    /**
     * Lấy danh sách công việc mà doanh nghiệp đã đăng
     */
    @Override
    public ApiResponse<Object> getAllJob() throws ParseException {
        // Lấy thông tin của business qua employee
        Business business = businessRepository.getBusinessByEmployeeId(getEmployeeViaToken().getId());
        if (business == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        // Lấy danh sách công việc của doanh nghiệp
        List<JobResponse> jobs = jobRepository.getAllJob(business.getId());
        if (jobs.isEmpty()) {
            throw new AppException(ERROR_NO_EXIST_JOB);
        }
        return ApiResponse.builder()
                .data(jobs)
                .build();
    }

    /**
     * Xem chi tiết công việc
     */
    @Override
    public ApiResponse<Object> getJobDetail(Integer jobId) {
        // Lấy thông tin chi tiết công việc
        Job job = jobRepository.getJobDetail(jobId);
        if (job == null) {
            throw new AppException(ERROR_NO_EXIST_JOB);
        }
        //Lấy thông tin industry qua job
        Industry industry = industryRepo.getIndustriesById(job.getIndustry().getId());
        if (industry == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        // Lấy thông tin business qua job
        Business business = businessRepository.getBusinessByEmployeeId(job.getBusiness().getId());
        if (business == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        // Lấy thông tin employee qua job
        Employee employee = employeeRepository.getEmployeeById(job.getEmployee().getId());
        if (employee == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        // Trả về jobDetailResponse
        JobDetailResponse jobDetailResponse = convertJob.toJobDetailResponse(job, industry, business, employee);
        return ApiResponse.builder()
                .data(jobDetailResponse)
                .build();
    }

    /**
     * Tạo công việc
     */
    @Override
    public ApiResponse<Object> createJob(JobRequest jobRequest) throws ParseException {
        // Cắt chuỗi token
        String token = tokenService.getJWT();
        // Lấy username từ token
        String usernameToken = tokenService.getClaim(token, "sub");
        UserAccount userAccount = userAccountRepository.findByUsername(usernameToken);
        if (userAccount == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        Industry industry = industryRepo.getIndustriesById(jobRequest.getIndustriesID());
        if (industry == null) {
            throw new AppException(ERROR_EXIST_INDUSTRY);
        }

        Business business = businessRepository.getBusinessByEmployeeId(getEmployeeViaToken().getId());
        if (business == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        Job job = Job.builder()
                .title(jobRequest.getTitle())
                .jobDescription(jobRequest.getJobDescription())
                .expireDate(jobRequest.getExpireDate())
                .level(jobRequest.getLevel())
                .salary(jobRequest.getSalary())
                .requirement(jobRequest.getRequirement())
                .benefit(jobRequest.getBenefit())
                .workingTime(jobRequest.getWorkingTime())
                .industry(industry)
                .business(business)
                .employee(getEmployeeViaToken())
                .statusBrowse(State.PENDING)
                .build();
        job.setStatus(Status.ACTIVE);
        job.setCreatedBy(usernameToken);
        jobRepository.save(job);
        JobResponse jobResponse = new JobResponse(job);
        return ApiResponse.builder()
                .data(jobResponse)
                .build();
    }

    /**
     * Cập nhật công việc
     */
    @Override
    public ApiResponse<Object> updateJob(Integer jobId, JobRequest jobRequest) throws ParseException {
        // Tìm job theo ID
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new AppException(ERROR_NO_EXIST_JOB));
        // Kiểm tra tên người tạo và tên đăng nhập của người đăng nhập
        // Nếu khác thì trả ra thông báo không được chỉnh sửa công việc
        if (job.getCreatedBy() == null || job.getCreatedBy().isEmpty()) {
            throw new AppException(ERROR_NO_EDIT_JOB);
        }
        if (!job.getCreatedBy().equals(getUsernameViaToken())) {
            throw new AppException(ERROR_NO_EDIT_JOB);
        }
        Industry industry = industryRepo.getIndustriesById(jobRequest.getIndustriesID());
        if (industry == null) {
            throw new AppException(ERROR_EXIST_INDUSTRY);
        }
        job.setTitle(jobRequest.getTitle());
        job.setJobDescription(jobRequest.getJobDescription());
        job.setExpireDate(jobRequest.getExpireDate());
        job.setLevel(jobRequest.getLevel());
        job.setSalary(jobRequest.getSalary());
        job.setRequirement(jobRequest.getRequirement());
        job.setBenefit(jobRequest.getBenefit());
        job.setWorkingTime(jobRequest.getWorkingTime());
        job.setIndustry(industry);
        job.setUpdatedBy(getUsernameViaToken());
        jobRepository.save(job);
        JobResponse jobResponse = new JobResponse(job);
        return ApiResponse.builder()
                .data(jobResponse)
                .build();
    }

    /**
     * Vô hiệu hóa công việc
     */
    @Override
    public ApiResponse<Object> inactiveJob(Integer jobId) throws ParseException {
        // Tìm job theo ID
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new AppException(ERROR_NO_EXIST_JOB));
        if (job == null) {
            throw new AppException(ERROR_NO_EXIST_JOB);
        }
        if (job.getStatus() == Status.INACTIVE) {
            throw new AppException(ErrorCode.ERROR_ALREADY_INACTIVE);
        }
        job.setStatus(Status.INACTIVE);
        job.setUpdatedBy(getUsernameViaToken());
        jobRepository.save(job);
        return new ApiResponse<>(INACTIVE_JOB);
    }


}