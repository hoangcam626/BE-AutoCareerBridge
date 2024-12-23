package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.converter.ConvertJob;
import com.backend.autocarrerbridge.dto.ApiResponse;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_ACCOUNT_IS_NULL;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_CODE_NOT_FOUND;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_EXIST_INDUSTRY;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_INVALID_JOB_STATE;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_JOB_ALREADY_APPROVED;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_JOB_ALREADY_REJECTED;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NOT_FOUND_BUSINESS;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_EDIT_JOB;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_EXIST_JOB;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_INACTIVE_JOB;
import static com.backend.autocarrerbridge.util.Constant.APPROVED_JOB;
import static com.backend.autocarrerbridge.util.Constant.INACTIVE_JOB;
import static com.backend.autocarrerbridge.util.Constant.REJECTED_JOB;

import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.notification.NotificationResponse;
import com.backend.autocarrerbridge.dto.response.industry.JobIndustryResponse;
import com.backend.autocarrerbridge.dto.response.job.BusinessJobResponse;
import com.backend.autocarrerbridge.dto.response.job.BusinessTotalResponse;
import com.backend.autocarrerbridge.service.NotificationService;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.backend.autocarrerbridge.dto.request.job.JobApprovedRequest;
import com.backend.autocarrerbridge.dto.request.job.JobRejectedRequest;
import com.backend.autocarrerbridge.dto.request.job.JobRequest;
import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.response.job.JobApprovedResponse;
import com.backend.autocarrerbridge.dto.response.job.JobDetailResponse;
import com.backend.autocarrerbridge.dto.response.job.JobRejectedResponse;
import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.util.Validation;
import com.backend.autocarrerbridge.util.email.EmailDTO;
import com.backend.autocarrerbridge.util.email.SendEmail;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Employee;
import com.backend.autocarrerbridge.entity.Industry;
import com.backend.autocarrerbridge.entity.Job;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.BusinessRepository;
import com.backend.autocarrerbridge.repository.EmployeeRepository;
import com.backend.autocarrerbridge.repository.IndustryRepository;
import com.backend.autocarrerbridge.repository.JobRepository;
import com.backend.autocarrerbridge.repository.UserAccountRepository;
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
    public ApiResponse<Object> getAllJob(int page, int size, String keyword, Pageable pageable) throws ParseException {
        Page<JobResponse> jobResponses = jobRepository.getAllJob(keyword, pageable);
        PagingResponse<JobResponse> pagingResponse = new PagingResponse<>(jobResponses);
        return ApiResponse.builder().data(pagingResponse).build();
    }

    /**
     * Lấy danh sách công việc mà doanh nghiệp đã đăng
     */
    @Override
    public ApiResponse<Object> getAllJobOfBusinessPaging(int page, int size, String keyword, Pageable pageable) throws ParseException {
        // Lấy danh sách công việc của doanh nghiệp
        Page<JobResponse> jobs = jobRepository.getAllJobOfBusinessPaging(getBusinessViaToken().getId(), keyword, pageable);

        // Kiểm tra và cập nhật trạng thái nếu expireDate quá ngày hôm nay
        LocalDate today = LocalDate.now();
        jobs.forEach(job -> {
            if (job.getExpireDate() != null && job.getExpireDate().isBefore(today)) {
                Job entity = findById(job.getJobId());
                if (entity != null && entity.getStatus() != Status.INACTIVE) {
                    entity.setStatus(Status.INACTIVE);
                    try {
                        entity.setUpdatedBy(getUsernameViaToken());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    jobRepository.save(entity);
                }
            }
        });
        PagingResponse<JobResponse> pagingResponse = new PagingResponse<>(jobs);
        return ApiResponse.builder().data(pagingResponse).build();
    }

    /**
     * Xem chi tiết công việc
     */
    @Override
    public ApiResponse<Object> getJobDetail(Integer jobId) throws ParseException {
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

        // Lấy thông tin employee qua job
        Employee employee = employeeRepository.getEmployeeById(job.getEmployee().getId());
        if (employee == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        // Trả về jobDetailResponse
        JobDetailResponse jobDetailResponse = convertJob.toJobDetailResponse(job, industry, getBusinessViaToken(), employee);
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
        job.setStatus(Status.ACTIVE);
        job.setStatusBrowse(State.PENDING);
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
        if (!job.getCreatedBy().equals(getUsernameViaToken())) {
            throw new AppException(ERROR_NO_INACTIVE_JOB);
        }
        if (job.getStatus() == Status.INACTIVE) {
            throw new AppException(ErrorCode.ERROR_ALREADY_INACTIVE);
        }
        job.setStatus(Status.INACTIVE);
        job.setUpdatedBy(getUsernameViaToken());
        jobRepository.save(job);
        return new ApiResponse<>(INACTIVE_JOB);
    }

    /**
     * Phê duyệt bài đăng công việc.
     */
    @Override
    public JobApprovedResponse approved(JobApprovedRequest req) throws ParseException {

        Job job = findById(req.getId());
        validateJobForStateChange(job, State.APPROVED);
        job.setStatusBrowse(State.APPROVED);
        jobRepository.save(job);

        String emailEmployee = job.getEmployee().getEmail();
        String emailBusiness = job.getBusiness().getEmail();

        EmailDTO emailEmployeeDTO = new EmailDTO(emailEmployee, APPROVED_JOB, "");
        sendEmail.sendApprovedJobNotification(emailEmployeeDTO,
                job.getTitle());
        EmailDTO emailBusinessDTO = new EmailDTO(emailBusiness, APPROVED_JOB, "");
        sendEmail.sendApprovedJobNotification(emailBusinessDTO,
                job.getTitle());


        String message = String.format("Tin tuyển dụng \"%s\" của bạn đã được phê duyệt", job.getTitle());
        NotificationSendRequest sendReq =NotificationSendRequest.of(Arrays.asList(emailBusiness, emailEmployee), APPROVED_JOB, message);
        NotificationResponse notification = notificationService.send(sendReq);
        return JobApprovedResponse.of(Boolean.TRUE, notification);
    }

    /**
     * Từ chối bài đăng công việc.
     */
    @Override
    public JobRejectedResponse rejected(JobRejectedRequest req) throws ParseException {
        Job job = findById(req.getId());
        validateJobForStateChange(job, State.REJECTED);
        job.setStatusBrowse(State.REJECTED);
        jobRepository.save(job);

        // Gửi thông báo email
        String emailEmployee = job.getEmployee().getEmail();
        EmailDTO emailDTO = new EmailDTO(emailEmployee, REJECTED_JOB, "");
        sendEmail.sendRRejectedJobNotification(emailDTO, job.getTitle(), req.getMessage());

        String emailBusiness = job.getBusiness().getEmail();
        EmailDTO emailBusinessDTO = new EmailDTO(emailBusiness, REJECTED_JOB, "");
        sendEmail.sendRRejectedJobNotification(emailBusinessDTO, job.getTitle(), req.getMessage());

        // Gửi thông báo hệ thống
        String message = String.format("Tin tuyển dụng \"%s\" của bạn đã bị từ chối. Lý do: %s", job.getTitle(), req.getMessage());
        NotificationSendRequest sendReq  = NotificationSendRequest.of(Arrays.asList(emailBusiness, emailEmployee), REJECTED_JOB, message);
        NotificationResponse notification = notificationService.send(sendReq);

        return JobRejectedResponse.of(Boolean.TRUE, notification);
    }

    /**
     * Lấy danh sách công việc theo trạng thái với phân trang.
     */
    @Override
    public PagingResponse<JobResponse> getPagingByState(PageInfo req, State state) {
        Pageable pageable = PageRequest.of(req.getPageNo(), req.getPageSize());
        String keyword = Validation.escapeKeywordForQuery(req.getKeyword());
        Page<JobResponse> res = jobRepository.findAllByState(pageable, state, keyword);
        return new PagingResponse<>(res);
    }

    @Override
    public ApiResponse<Object> checkDeletePermission(Integer jobId) throws ParseException {
        // kiểm tra quyền
        boolean hasPermission = checkInactive(jobId);

        return ApiResponse.builder().data(hasPermission).build();
    }

    /**
     * Tìm bài đăng công việc theo ID.
     */
    public Job findById(Integer id) {
        return jobRepository.findById(id).orElseThrow(() -> new AppException(ERROR_NO_EXIST_JOB));
    }

    /**
     * Kiểm tra tính hợp lệ của trạng thái bài đăng trước khi thay đổi.
     */
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
    @Override
    public List<BusinessTotalResponse> getBusinessJob(Pageable pageable) {
        return jobRepository.findAllBusinessWithTotalJob(pageable).getContent();
    }

    @Override
    public PagingResponse<BusinessJobResponse>getJobBusinessByRegion(Pageable pageable, Integer regionId) {
        Page<BusinessJobResponse> page = jobRepository.findJobByRegion(pageable,regionId);
        if(Objects.isNull(page) || page.getContent().isEmpty()){
            throw  new AppException(ErrorCode.ERROR_NO_CONTENT);
        }
        // Lấy danh sách Job từ repository
        return new PagingResponse<>(page);
    }

    @Override
    public PagingResponse<BusinessJobResponse> getJobBusinessByProvince(Pageable pageable, Integer provinceId) {
        Page<BusinessJobResponse> page = jobRepository.findJobByProvince(pageable,provinceId);
        if(Objects.isNull(page) || page.getContent().isEmpty()){
            throw  new AppException(ErrorCode.ERROR_NO_CONTENT);
        }
        // Lấy danh sách Job từ repository
        return new PagingResponse<>(page);
    }

    @Override
    public PagingResponse<BusinessJobResponse> getJobBusinessByDistrict(Pageable pageable, Integer districtId) {
        Page<BusinessJobResponse> page = jobRepository.findJobByDistrict(pageable,districtId);
        if(Objects.isNull(page) || page.getContent().isEmpty()){
            throw  new AppException(ErrorCode.ERROR_NO_CONTENT);
        }
        return new PagingResponse<>(page);
    }

    @Override
    public PagingResponse<BusinessJobResponse> getAllJobBusiness(Pageable pageable) {
        Page<BusinessJobResponse> page = jobRepository.findAllJobBusiness(pageable);
        if(Objects.isNull(page) || page.getContent().isEmpty()){
            throw  new AppException(ErrorCode.ERROR_NO_CONTENT);
        }
        return new PagingResponse<>(page);
    }

    @Override
    public PagingResponse<BusinessJobResponse> getAllJobBusinessBySalary(Pageable pageable, Long minSalary, Long maxSalary) {
        Page<BusinessJobResponse> page = jobRepository.findJobBySalary(pageable,minSalary,maxSalary);
        if(Objects.isNull(page) || page.getContent().isEmpty()){
            throw  new AppException(ErrorCode.ERROR_NO_CONTENT);
        }
        return new PagingResponse<>(page);
    }

    @Override
    public PagingResponse<BusinessJobResponse> getAllJobBusinessByIndustry(Pageable pageable, Integer industryId) {
        Page<BusinessJobResponse> page = jobRepository.findJobByIndustry(pageable,industryId);
        if(Objects.isNull(page) || page.getContent().isEmpty()){
            throw  new AppException(ErrorCode.ERROR_NO_CONTENT);
        }
        return new PagingResponse<>(page);
    }

    @Override
    public List<JobIndustryResponse> getTotalJobByIndustry(Pageable pageable) {
        return jobRepository.findTotalJobByIndustry(pageable).getContent();
    }


    /**
     * Kiểm tra xem người dùng có quyền vô hiệu hóa công việc này không
     */
    private boolean checkInactive(Integer jobId) throws ParseException {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new AppException(ERROR_NO_INACTIVE_JOB));

        // Lấy thông tin người dùng hiện tại
        // Kiểm tra quyền xóa
        return job.getCreatedBy().equals(getUsernameViaToken());
    }
}