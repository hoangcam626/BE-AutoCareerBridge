package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.business.sdi.BusinessApprovedSdi;
import com.backend.autocarrerbridge.dto.university.sdi.UniversityApprovedSdi;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.BussinessRepository;
import com.backend.autocarrerbridge.repository.UniversityRepository;
import com.backend.autocarrerbridge.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl {

    private final BussinessRepository bussinessRepository;
    private final UniversityRepository universityRepository;
    private final UserAccountService userAccountService;

    public void approvedBusiness(BusinessApprovedSdi req){
        Business business = bussinessRepository.findById(req.getId()).orElseThrow(() -> new AppException(ErrorCode.ERROR_NOT_FOUND_BUSINESS));
        userAccountService.approvedAccount(business.getUserAccount());
    }

    public void approvedUniversity(UniversityApprovedSdi req){
        University university = universityRepository.findById(req.getId()).orElseThrow(() -> new AppException(ErrorCode.ERROR_NOT_FOUND_BUSINESS));
        userAccountService.approvedAccount(university.getUserAccount());
    }

}
