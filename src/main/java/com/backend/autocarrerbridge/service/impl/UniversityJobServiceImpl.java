package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.request.universityjob.UniversityJobRequest;
import com.backend.autocarrerbridge.dto.response.job.JobResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.dto.response.universityjob.UniversityJobResponse;
import com.backend.autocarrerbridge.entity.Job;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.UniversityJob;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.repository.UniversityJobRepository;
import com.backend.autocarrerbridge.service.JobService;
import com.backend.autocarrerbridge.service.NotificationService;
import com.backend.autocarrerbridge.service.UniversityJobService;
import com.backend.autocarrerbridge.service.UniversityService;
import com.backend.autocarrerbridge.util.Validation;
import com.backend.autocarrerbridge.util.enums.State;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_PENDING_UNIVERSITY_JOB;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_UNIVERSITY_JOB_EXITS;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_UNIVERSITY_JOB_NOT_FOUND;
import static com.backend.autocarrerbridge.util.Constant.APPROVED_UNIVERSITY_JOB;
import static com.backend.autocarrerbridge.util.Constant.REJECTED_UNIVERSITY_JOB;

@Service
@RequiredArgsConstructor
@Transactional
public class UniversityJobServiceImpl implements UniversityJobService {
    private final UniversityJobRepository universityJobRepository;
    private final JobService jobRepository;
    private final UniversityService universityRepository;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;

    @Override
    public UniversityJobResponse findById(Integer id) {
        return null;
    }

    @Override
    public UniversityJobResponse create(UniversityJobRequest res) {
        if (universityJobRepository.checkExistUniversityJob(res.getUniversityId(), res.getJobId())) {
            throw new AppException(ERROR_UNIVERSITY_JOB_EXITS);
        }
        Job job = jobRepository.findById(res.getJobId());
        University university = universityRepository.findById(res.getUniversityId());
        UniversityJob universityJob = UniversityJob.builder()
                .job(job)
                .university(university)
                .statusConnected(State.PENDING)
                .build();
        universityJob = universityJobRepository.save(universityJob);
        return mapData(universityJob);
    }

    @Override
    public UniversityJobResponse approve(UniversityJobRequest res) throws ParseException {
        UniversityJob uj = universityJobRepository.findtUniversityJob(res.getUniversityId(), res.getJobId());
        if (Objects.isNull(uj)) {
            throw new AppException(ERROR_UNIVERSITY_JOB_NOT_FOUND);
        }
        if (uj.getStatusConnected() != State.PENDING) {
            throw new AppException(ERROR_NO_PENDING_UNIVERSITY_JOB);
        }
        uj.setStatusConnected(State.APPROVED);
        uj = universityJobRepository.save(uj);

        String message = String.format("Yêu cầu ứng tuyển của bạn vào %s đã được chấp thuận. Hãy kiểm tra và liên hệ với nhà tuyển dụng.", uj.getJob().getTitle());
        notificationService.send(NotificationSendRequest.of(Collections.singletonList(uj.getUniversity().getEmail()), APPROVED_UNIVERSITY_JOB, message));
        return mapData(uj);
    }

    @Override
    public UniversityJobResponse reject(UniversityJobRequest res) throws ParseException {
        UniversityJob uj = universityJobRepository.findtUniversityJob(res.getUniversityId(), res.getJobId());
        if (Objects.isNull(uj)) {
            throw new AppException(ERROR_UNIVERSITY_JOB_NOT_FOUND);
        }
        if (uj.getStatusConnected() != State.PENDING) {
            throw new AppException(ERROR_NO_PENDING_UNIVERSITY_JOB);
        }
        uj.setStatusConnected(State.REJECTED);
        uj = universityJobRepository.save(uj);

        String message = String.format("Yêu cầu ứng tuyển của bạn vào %s đã bị từ chối.", uj.getJob().getTitle());
        notificationService.send(NotificationSendRequest.of(Collections.singletonList(uj.getUniversity().getEmail()), REJECTED_UNIVERSITY_JOB, message));
        return mapData(uj);
    }

    @Override
    public List<UniversityResponse> getUniversityApplyJob(Integer jobId, State state) {
        List<University> res = universityJobRepository.getAllUniversity(jobId, state);
        return res.stream().map(u -> modelMapper.map(u, UniversityResponse.class)).toList();
    }

    @Override
    public PagingResponse<UniversityJobResponse> getAllJobsInUniversity(Integer universityId, String state, PageInfo req) {
        State statusConnect = null;
        if(!state.isBlank()){
            statusConnect = State.valueOf(state);
        }

        Pageable pageable = PageRequest.of(req.getPageNo(), req.getPageSize());
        String keyword = Validation.escapeKeywordForQuery(req.getKeyword());
        Page<UniversityJob> jobs = universityJobRepository.getAllJobs(universityId, statusConnect, keyword, pageable);
        Page<UniversityJobResponse> res = jobs.map(this::mapData);
        return new PagingResponse<>(res);
    }

    @Override
    public String getStatus(Integer universityId, Integer jobId) {
        UniversityJob uj = universityJobRepository.findtUniversityJob(universityId, jobId);
        if (Objects.isNull(uj)) {
            return "";
        }
        return uj.getStatusConnected().toString();
    }

    private UniversityJobResponse mapData(UniversityJob uj) {
        UniversityJobResponse res = modelMapper.map(uj, UniversityJobResponse.class);
        res.setJobResponse(modelMapper.map(uj.getJob(), JobResponse.class));
        res.setUniversityResponse(modelMapper.map(uj.getUniversity(), UniversityResponse.class));
        return res;
    }
}
