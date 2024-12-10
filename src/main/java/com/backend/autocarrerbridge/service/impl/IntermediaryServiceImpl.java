package com.backend.autocarrerbridge.service.impl;

import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.controller.repository.BusinessRepository;
import com.backend.autocarrerbridge.controller.repository.SubAdminRepository;
import com.backend.autocarrerbridge.controller.repository.UniversityRepository;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.SubAdmin;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.service.IntermediaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntermediaryServiceImpl implements IntermediaryService {
    private final UniversityRepository universityRepository;
    private final BusinessRepository businessRepository;
    private final SubAdminRepository subAdminRepository;

    @Override
    public University findUniversityByEmail(String email) {
        return universityRepository.findByEmail(email);
    }

    @Override
    public Business findBusinessByEmail(String email) {
        return businessRepository.findByEmail(email);
    }

    @Override
    public SubAdmin findSubAdminByEmail(String email) {
        return subAdminRepository.findByEmail(email);
    }
}
