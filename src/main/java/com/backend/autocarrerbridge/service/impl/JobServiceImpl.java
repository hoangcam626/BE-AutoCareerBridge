package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_ACCOUNT_IS_NULL;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_CODE_NOT_FOUND;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_EXIST_INDUSTRY;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_INVALID_JOB_STATE;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_JOB_ALREADY_APPROVED;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_JOB_ALREADY_REJECTED;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NOT_FOUND_BUSINESS;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_EDIT_JOB;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_EXIST_JOB;
import static com.backend.autocarrerbridge.util.Constant.APPROVED_JOB;
import static com.backend.autocarrerbridge.util.Constant.INACTIVE_JOB;
import static com.backend.autocarrerbridge.util.Constant.REJECTED_JOB;

import java.text.ParseException;

import com.backend.autocarrerbridge.dto.request.job.JobApprovedRequest;
import com.backend.autocarrerbridge.dto.request.job.JobRejectedRequest;
import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.response.job.JobApprovedResponse;
import com.backend.autocarrerbridge.dto.response.job.JobRejectedResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.service.NotificationService;
import com.backend.autocarrerbridge.util.email.EmailDTO;
import com.backend.autocarrerbridge.util.email.SendEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.converter.ConvertJob;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.job.JobRequest;
import com.backend.autocarrerbridge.dto.response.job.JobDetailResponse;
import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Employee;
import com.backend.autocarrerbridge.entity.Industry;
import com.backend.autocarrerbridge.entity.Job;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.controller.repository.BusinessRepository;
import com.backend.autocarrerbridge.controller.repository.EmployeeRepository;
import com.backend.autocarrerbridge.controller.repository.IndustryRepository;
import com.backend.autocarrerbridge.controller.repository.JobRepository;
import com.backend.autocarrerbridge.controller.repository.UserAccountRepository;
import com.backend.autocarrerbridge.service.JobService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final TokenService tokenService;
    private final UserAccountRepository userAccountRepository;
    private final IndustryRepository industryRepository;
    private final BusinessRepository businessRepository;
    private final EmployeeRepository employeeRepository;
    private final NotificationService notificationService;
    private final ConvertJob convertJob;
    private final SendEmail sendEmail;

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
            throw new AppException(ERROR_ACCOUNT_IS_NULL);
        }
        return employeeToken;
    }

    /**
     * Lấy Business từ token
     */
    public Business getBusinessViaToken() throws ParseException {
        // Cắt chuỗi token
        String token = tokenService.getJWT();
        // Lấy username từ token
        String usernameToken = tokenService.getClaim(token, "sub");
        Business businessToken = businessRepository.findByUsername(usernameToken);
        if (businessToken == null) {
            throw new AppException(ERROR_NOT_FOUND_BUSINESS);
        }
        return businessToken;
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
     * Lấy danh sách tất cả công việc
     */
    @Override
    public ApiResponse<Object> getAllJob(int page, int size, String keyword, Pageable pageable) throws ParseException {
        return ApiResponse.builder().data(jobRepository.getAllJob(keyword, pageable)).build();
    }

    /**
     * Lấy danh sách công việc mà doanh nghiệp đã đăng
     */
    @Override
    public ApiResponse<Object> getAllJobOfBusiness(int page, int size, String keyword, Pageable pageable) throws ParseException {
        // Lấy danh sách công việc của doanh nghiệp
        Page<JobResponse> jobs = jobRepository.getAllJobOfBusiness(getBusinessViaToken().getId(), keyword, pageable);
        if (jobs.isEmpty()) {
            throw new AppException(ERROR_NO_EXIST_JOB);
        }
        PagingResponse<JobResponse> pagingResponse = new PagingResponse<>(jobs);
        return ApiResponse.builder().data(pagingResponse).build();
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
        // Lấy thông tin industry qua job
        Industry industry = industryRepository.getIndustriesById(job.getIndustry().getId());
        if (industry == null) {
            throw new AppException(ERROR_EXIST_INDUSTRY);
        }
        // Lấy thông tin business qua job
        Business business =
                businessRepository.getBusinessByEmployeeId(job.getBusiness().getId());
        if (business == null) {
            throw new AppException(ERROR_NOT_FOUND_BUSINESS);
        }
        // Lấy thông tin employee qua job
        Employee employee = employeeRepository.getEmployeeById(job.getEmployee().getId());
        if (employee == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        // Trả về jobDetailResponse
        JobDetailResponse jobDetailResponse = convertJob.toJobDetailResponse(job, industry, business, employee);
        return ApiResponse.builder().data(jobDetailResponse).build();
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
            throw new AppException(ERROR_ACCOUNT_IS_NULL);
        }
        Industry industry = industryRepository.getIndustriesById(jobRequest.getIndustriesID());
        if (industry == null) {
            throw new AppException(ERROR_EXIST_INDUSTRY);
        }

        Business business =
                businessRepository.getBusinessByEmployeeId(getEmployeeViaToken().getId());
        if (business == null) {
            throw new AppException(ERROR_NOT_FOUND_BUSINESS);
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
        return ApiResponse.builder().data(jobResponse).build();
    }

    /**
     * Cập nhật công việc
     */
    @Override
    public ApiResponse<Object> updateJob(Integer jobId, JobRequest jobRequest) throws ParseException {
        // Tìm job theo ID
        Job job = findById(jobId);
        // Kiểm tra tên người tạo và tên đăng nhập của người đăng nhập
        // Nếu khác thì trả ra thông báo không được chỉnh sửa công việc
        if (job.getCreatedBy() == null || job.getCreatedBy().isEmpty()) {
            throw new AppException(ERROR_NO_EDIT_JOB);
        }
        if (!job.getCreatedBy().equals(getUsernameViaToken())) {
            throw new AppException(ERROR_NO_EDIT_JOB);
        }
        Industry industry = industryRepository.getIndustriesById(jobRequest.getIndustriesID());
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
        return ApiResponse.builder().data(jobResponse).build();
    }

    /**
     * Vô hiệu hóa công việc
     */
    @Override
    public ApiResponse<Object> inactiveJob(Integer jobId) throws ParseException {
        // Tìm job theo ID
        Job job = findById(jobId);
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

    @Override
    public JobApprovedResponse approved(JobApprovedRequest req) throws ParseException {

        Job job = findById(req.getId());
        validateJobForStateChange(job, State.APPROVED);
        job.setStatusBrowse(State.APPROVED);
        String emailEmployee = job.getEmployee().getEmail();

        EmailDTO emailDTO = new EmailDTO(emailEmployee, APPROVED_JOB, "");
        sendEmail.sendApprovedJobNotification(emailDTO,
                job.getTitle());

        String message = String.format("%s: %s", APPROVED_JOB, job.getTitle());
        notificationService.send(NotificationSendRequest.of(emailEmployee, message));
        return JobApprovedResponse.of(Boolean.TRUE);
    }

    @Override
    public JobRejectedResponse rejected(JobRejectedRequest req) throws ParseException {
        Job job = findById(req.getId());
        validateJobForStateChange(job, State.REJECTED);
        job.setStatusBrowse(State.REJECTED);
        String emailEmployee = job.getEmployee().getEmail();
        // Gửi thông báo email
        EmailDTO emailDTO = new EmailDTO(emailEmployee, REJECTED_JOB, "");
        sendEmail.sendRRejectedJobNotification(emailDTO, job.getTitle(), req.getMessage());
        // Gửi thông báo hệ thống
        String message = String.format("%s: %s. Lý do: %s", REJECTED_JOB, job.getTitle(), req.getMessage());
        notificationService.send(NotificationSendRequest.of(emailEmployee, message));

        return JobRejectedResponse.of(req.getMessage());
    }

    public Job findById(Integer id) {
        return jobRepository.findById(id).orElseThrow(() -> new AppException(ERROR_NO_EXIST_JOB));
    }

    private void validateJobForStateChange(Job req, State targetState) {

        // Kiểm tra nếu trạng thái hiện tại giống với trạng thái mục tiêu
        if (req.getStatusBrowse() == targetState) {
            throw new AppException(targetState == State.APPROVED
                    ? ERROR_JOB_ALREADY_APPROVED
                    : ERROR_JOB_ALREADY_REJECTED);
        }

        // Kiểm tra trạng thái không hợp lệ (chỉ cho phép thay đổi từ PENDING)
        if (req.getStatusBrowse() != State.PENDING) {
            throw new AppException(ERROR_INVALID_JOB_STATE);
        }
    }
}