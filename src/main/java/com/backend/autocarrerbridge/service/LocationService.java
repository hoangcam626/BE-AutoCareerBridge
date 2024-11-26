package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.location.LocationRequest;
import com.backend.autocarrerbridge.entity.Location;

public interface LocationService {
    Location getLocationByProvinceDistrictWard(LocationRequest locationRequest);
    Location saveLocation(LocationRequest locationRequest);
}
