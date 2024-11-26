package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.repository.AdminstrativeUnitRepository;
import com.backend.autocarrerbridge.repository.DistrictRepository;
import com.backend.autocarrerbridge.service.AdminstrativeUnitService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminstrativeUnitServiceIpml implements AdminstrativeUnitService {
    AdminstrativeUnitRepository adminstrativeUnitRepository;


}
