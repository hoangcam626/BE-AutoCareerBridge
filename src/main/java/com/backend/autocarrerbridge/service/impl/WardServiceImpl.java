package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_DISTRICT_NOT_FOUND;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.response.ward.WardResponse;
import com.backend.autocarrerbridge.entity.Ward;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.repository.WardRepository;
import com.backend.autocarrerbridge.service.WardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WardServiceImpl implements WardService {

    private final WardRepository wardRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<WardResponse> getAllByDistrictId(Integer districtId) {

        List<Ward> wards = wardRepository.findByDistrictId(districtId);

        return wards.stream()
                .map(district -> modelMapper.map(district, WardResponse.class))
                .toList();
    }

    @Override
    public WardResponse getById(Integer id) {

        Ward ward = wardRepository.findById(id).orElseThrow(() -> new AppException(ERROR_DISTRICT_NOT_FOUND));
        return modelMapper.map(ward, WardResponse.class);
    }

    @Override
    public Ward findWardById(Integer id) {
        return wardRepository.findById(id).orElseThrow(() -> new AppException(ERROR_DISTRICT_NOT_FOUND));
    }
}
