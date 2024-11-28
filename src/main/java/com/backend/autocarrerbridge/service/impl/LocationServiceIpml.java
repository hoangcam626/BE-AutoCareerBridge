package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.request.location.LocationRequest;
import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.LocationRepository;
import com.backend.autocarrerbridge.service.DistrictService;
import com.backend.autocarrerbridge.service.LocationService;
import com.backend.autocarrerbridge.service.ProvinceService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.WardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Objects;

import static com.backend.autocarrerbridge.util.Constant.SUB;

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
        Location location = new Location();
        try {
            var emailAccountLogin=tokenService.getClaim(tokenService.getJWT(),SUB);

            //check xem location của business đã có chưa nếu có thì lấy ra sửa
            if(Objects.nonNull(request.getId())) {
                location= locationRepository.findById(request.getId()).orElseThrow(()-> new AppException(ErrorCode.ERROR_LOCATION_NOT_FOUND));
                location.setUpdatedBy(emailAccountLogin);
            }else {
                location.setCreatedBy(emailAccountLogin);
            }
            location.setDescription(request.getDescription());
            location.setProvince(provinceService.findProvinceById(request.getProvinceId()));
            location.setDistrict(districtService.findDistrictById(request.getDistrictId()));
            location.setWard(wardService.findWardById(request.getWardId()));

        } catch (ParseException e) {
            throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
        }
        return locationRepository.save(location);
    }


}
