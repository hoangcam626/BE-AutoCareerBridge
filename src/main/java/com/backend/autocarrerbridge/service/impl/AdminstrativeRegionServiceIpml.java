package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.repository.AdminstrativeRegionRepository;
import com.backend.autocarrerbridge.repository.DistrictRepository;
import com.backend.autocarrerbridge.service.AdminstrativeRegionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminstrativeRegionServiceIpml implements AdminstrativeRegionService {
    AdminstrativeRegionRepository adminstrativeRegionRepository;



}
