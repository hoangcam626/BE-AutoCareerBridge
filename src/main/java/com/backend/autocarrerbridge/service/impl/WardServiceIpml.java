package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.entity.Ward;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.WardRepository;
import com.backend.autocarrerbridge.service.WardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WardServiceIpml implements WardService {
    WardRepository wardRepository;

    @Override
    public Ward findWardById(Integer id) {
        return wardRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ERROR_WARD_NOT_FOUND));
    }
}
