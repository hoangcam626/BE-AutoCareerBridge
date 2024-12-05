package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.location.LocationRequest;
import com.backend.autocarrerbridge.entity.Location;

public interface LocationService {

    Location saveLocation(LocationRequest locationRequest);

}
