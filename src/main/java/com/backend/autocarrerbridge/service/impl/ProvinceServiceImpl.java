package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_PROVINCE_NOT_BLANK;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.controller.repository.ProvinceRepository;
import com.backend.autocarrerbridge.dto.response.province.ProvinceResponse;
import com.backend.autocarrerbridge.entity.Province;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.service.ProvinceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProvinceResponse> getAll() {
        List<Province> provinces = provinceRepository.findAll();
        return provinces.stream()
                .map(province -> modelMapper.map(province, ProvinceResponse.class))
                .toList();
    }

    @Override
    public ProvinceResponse getById(Integer id) {
        if (Objects.isNull(id)) {
            throw new AppException(ERROR_PROVINCE_NOT_BLANK);
        }
        Province province = findProvinceById(id);
        return modelMapper.map(province, ProvinceResponse.class);
    }

    @Override
    public Province findProvinceById(Integer id) {
        return provinceRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_PROVINCE_NOT_FOUND));
    }
}
