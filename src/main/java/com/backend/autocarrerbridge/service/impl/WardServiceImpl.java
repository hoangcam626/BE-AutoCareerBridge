package com.backend.autocarrerbridge.service.impl;



import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.response.ward.WardResponse;
import com.backend.autocarrerbridge.entity.Ward;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.repository.WardRepository;
import com.backend.autocarrerbridge.service.WardService;

import lombok.RequiredArgsConstructor;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_DISTRICT_NOT_BLANK;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_DISTRICT_NOT_FOUND;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_WARD_NOT_BLANK;

@Service
@RequiredArgsConstructor
public class WardServiceImpl implements WardService {

    private final WardRepository wardRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<WardResponse> getAllByDistrictId(Integer districtId) {

        if (Objects.isNull(districtId)) {
            throw new AppException(ERROR_DISTRICT_NOT_BLANK);
        }

        List<Ward> wards = wardRepository.findByDistrictId(districtId);

        return wards.stream()
                .map(district -> modelMapper.map(district, WardResponse.class))
                .toList();
    }

    @Override
    public WardResponse getById(Integer id) {

        if (Objects.isNull(id)) {
            throw new AppException(ERROR_WARD_NOT_BLANK);
        }
        Ward ward = findWardById(id);
        return modelMapper.map(ward, WardResponse.class);
    }

    @Override
    public Ward findWardById(Integer id) {
        return wardRepository.findById(id).orElseThrow(() -> new AppException(ERROR_DISTRICT_NOT_FOUND));
    }
}
