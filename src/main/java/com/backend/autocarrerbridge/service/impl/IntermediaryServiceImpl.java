package com.backend.autocarrerbridge.service.impl;


import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.repository.BusinessRepository;
import com.backend.autocarrerbridge.repository.UniversityRepository;

import com.backend.autocarrerbridge.service.IntermediaryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntermediaryServiceImpl implements IntermediaryService {
    private final UniversityRepository universityRepository;
    private final BusinessRepository businessRepository;
    @Override
    public University findUniversityByEmail(String email) {
        return universityRepository.findByEmail(email);
    }

    @Override
    public Business findBusinessByEmail(String email) {
        return businessRepository.findByEmail(email);
    }
}
