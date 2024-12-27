package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.response.statistic.AdminStatisticResponse;
import com.backend.autocarrerbridge.dto.response.statistic.DateStatisticResponse;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.BusinessRepository;
import com.backend.autocarrerbridge.repository.BusinessUniversityRepository;
import com.backend.autocarrerbridge.repository.JobRepository;
import com.backend.autocarrerbridge.repository.UniversityRepository;
import com.backend.autocarrerbridge.repository.WorkShopBusinessRepository;
import com.backend.autocarrerbridge.repository.WorkShopRepository;
import com.backend.autocarrerbridge.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final BusinessRepository businessRepository;
    private final UniversityRepository universityRepository;
    private final JobRepository jobRepository;
    private final WorkShopRepository workShopRepository;
    private final WorkShopBusinessRepository workShopBusinessRepository;
    private final BusinessUniversityRepository businessUniversityRepository;

    public AdminStatisticResponse total() {
        AdminStatisticResponse res = new AdminStatisticResponse();
        res.setBusinessesTotal(businessRepository.countBusiness());
        res.setJobsTotal(jobRepository.countJob());
        res.setWorkshopsTotal(workShopRepository.countWorkshop());
        res.setUniversityBusinessTotal(businessUniversityRepository.countBusinessUniversity());
        return res;
    }

    public Map<LocalDate, DateStatisticResponse> totalOnDate(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) {
            throw new AppException(ErrorCode.ERROR_WORK_SHOP_DATE);
        }
        Map<LocalDate, DateStatisticResponse> res = new LinkedHashMap<>();
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) { // Sử dụng isAfter để kiểm tra phạm vi
            DateStatisticResponse dateRes = new DateStatisticResponse();
            dateRes.setJobsNo(jobRepository.countJobByDate(date));
            dateRes.setWorkshopsNo(workShopRepository.countWorkshopByDate(date));
            dateRes.setUniversityBusinessNo(businessUniversityRepository.countBusinessUniversityByDate(date));
            dateRes.setWorkshopsBusinessesNo(workShopBusinessRepository.countWorkshopBusinessByDate(date));
            res.put(date, dateRes);
            date = date.plusDays(1);
        }
        return res;
    }
}
