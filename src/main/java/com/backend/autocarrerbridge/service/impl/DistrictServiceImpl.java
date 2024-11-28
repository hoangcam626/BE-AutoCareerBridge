package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_DISTRICT_NOT_FOUND;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.response.district.DistrictResponse;
import com.backend.autocarrerbridge.entity.District;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.repository.DistrictRepository;
import com.backend.autocarrerbridge.service.DistrictService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<DistrictResponse> getAllByProvinceId(Integer provinceId) {
        List<District> districts = districtRepository.findByProvinceId(provinceId);

        return districts.stream()
                .map(district -> modelMapper.map(district, DistrictResponse.class))
                .toList();
    }

    @Override
    public DistrictResponse getById(Integer id) {
        District district =
                districtRepository.findById(id).orElseThrow(() -> new AppException(ERROR_DISTRICT_NOT_FOUND));
        return modelMapper.map(district, DistrictResponse.class);
    }

    @Override
    public District findDistrictById(Integer id) {
        return districtRepository.findById(id).orElseThrow(() -> new AppException(ERROR_DISTRICT_NOT_FOUND));
    }
}
