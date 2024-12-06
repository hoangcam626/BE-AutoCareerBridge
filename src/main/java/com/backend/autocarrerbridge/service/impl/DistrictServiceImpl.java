package com.backend.autocarrerbridge.service.impl;



import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.response.district.DistrictResponse;
import com.backend.autocarrerbridge.entity.District;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.controller.repository.DistrictRepository;
import com.backend.autocarrerbridge.service.DistrictService;

import lombok.RequiredArgsConstructor;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_DISTRICT_NOT_BLANK;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_DISTRICT_NOT_FOUND;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_PROVINCE_NOT_BLANK;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<DistrictResponse> getAllByProvinceId(Integer provinceId) {

        if (Objects.isNull(provinceId)) {
            throw new AppException(ERROR_PROVINCE_NOT_BLANK);
        }

        List<District> districts = districtRepository.findByProvinceId(provinceId);

        return districts.stream()
                .map(district -> modelMapper.map(district, DistrictResponse.class))
                .toList();
    }

    @Override
    public DistrictResponse getById(Integer id) {

        if (Objects.isNull(id)) {
            throw new AppException(ERROR_DISTRICT_NOT_BLANK);
        }
        District district = findDistrictById(id);
        return modelMapper.map(district, DistrictResponse.class);
    }

    @Override
    public District findDistrictById(Integer id) {
        return districtRepository.findById(id).orElseThrow(() -> new AppException(ERROR_DISTRICT_NOT_FOUND));
    }
}
