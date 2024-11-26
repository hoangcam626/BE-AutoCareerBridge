package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.entity.Province;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.ProvinceRepository;
import com.backend.autocarrerbridge.service.ProvinceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProvinceServiceIpml implements ProvinceService {
    ProvinceRepository provinceRepository;

    @Override
    public Province findProvinceById(Integer id) {
        Province province=provinceRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ERROR_PROVINCE_NOT_FOUND));
        return province;
    }

}
