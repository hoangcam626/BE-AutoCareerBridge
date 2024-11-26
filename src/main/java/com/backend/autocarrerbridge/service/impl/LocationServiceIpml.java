package com.backend.autocarrerbridge.service.impl;

import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.location.LocationRequest;
import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.repository.LocationRepository;
import com.backend.autocarrerbridge.service.DistrictService;
import com.backend.autocarrerbridge.service.LocationService;
import com.backend.autocarrerbridge.service.ProvinceService;
import com.backend.autocarrerbridge.service.WardService;

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

    @Override
    public Location getLocationByProvinceDistrictWard(LocationRequest request) {
        Location location = null;
        location.setProvinces(provinceService.findProvinceById(request.getProvinceId()));
        location.setDistricts(districtService.findDistrictById(request.getDistrictId()));
        location.setWards(wardService.findWardById(request.getWardId()));
        return location;
    }

    @Override
    public Location saveLocation(LocationRequest request) {
        Location location = new Location();

        location.setDescription(request.getDescription());
        location.setProvinces(provinceService.findProvinceById(request.getProvinceId()));
        location.setDistricts(districtService.findDistrictById(request.getDistrictId()));
        location.setWards(wardService.findWardById(request.getWardId()));

        return locationRepository.save(location);
    }
}
