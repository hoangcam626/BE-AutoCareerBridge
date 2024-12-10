package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.util.Constant.SUB;

import com.backend.autocarrerbridge.service.DistrictService;
import com.backend.autocarrerbridge.service.ProvinceService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.WardService;
import java.text.ParseException;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.location.LocationRequest;
import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.LocationRepository;
import com.backend.autocarrerbridge.service.LocationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationServiceIpml implements LocationService {

    LocationRepository locationRepository;
    ProvinceService provinceService;
    DistrictService districtService;
    WardService wardService;
    TokenService tokenService;

    @Override
    public Location saveLocation(LocationRequest request) {
        Location location;

        try {
            var emailAccountLogin = tokenService.getClaim(tokenService.getJWT(), SUB);

            // Nếu có ID (Cập nhật)
            if (Objects.nonNull(request.getId())) {
                location = locationRepository
                        .findById(request.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.ERROR_LOCATION_NOT_FOUND));

                // Cập nhật thông tin
                location.setUpdatedBy(emailAccountLogin); // Cập nhật thông tin người sửa

                // Cập nhật các thông tin nếu có sự thay đổi
                if (request.getDescription() != null && !request.getDescription().equals(location.getDescription())) {
                    location.setDescription(request.getDescription());
                }
                if (request.getProvinceId() != null && !Objects.equals(request.getProvinceId(), location.getProvince().getId())) {
                    location.setProvince(provinceService.findProvinceById(request.getProvinceId()));
                }
                if (request.getDistrictId() != null && !Objects.equals(request.getDistrictId(), location.getDistrict().getId())) {
                    location.setDistrict(districtService.findDistrictById(request.getDistrictId()));
                }
                if (request.getWardId() != null && !Objects.equals(request.getWardId(), location.getWard().getId())) {
                    location.setWard(wardService.findWardById(request.getWardId()));
                }

            } else {

                location = new Location();
                location.setCreatedBy(emailAccountLogin);

                if (request.getDescription() != null) {
                    location.setDescription(request.getDescription());
                }
                if (request.getProvinceId() != null) {
                    location.setProvince(provinceService.findProvinceById(request.getProvinceId()));
                }
                if (request.getDistrictId() != null) {
                    location.setDistrict(districtService.findDistrictById(request.getDistrictId()));
                }
                if (request.getWardId() != null) {
                    location.setWard(wardService.findWardById(request.getWardId()));
                }
            }

        } catch (ParseException e) {
            throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
        }

        return locationRepository.save(location);
    }

}
