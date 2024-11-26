package com.backend.autocarrerbridge.service.impl;

import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.entity.District;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.DistrictRepository;
import com.backend.autocarrerbridge.service.DistrictService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DistrictServiceIpml implements DistrictService {
    DistrictRepository districtRepository;

    @Override
    public District findDistrictById(Integer id) {
        return districtRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_DISTRICT_NOT_FOUND));
    }
}
