package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.response.university.UniversityRegisterResponse;
import com.backend.autocarrerbridge.dto.request.account.UserUniversityRequest;
import com.backend.autocarrerbridge.entity.University;


public interface UniversityService {
    UniversityRegisterResponse registerUniversity(UserUniversityRequest userUniversityRequest);

    University findById(Integer id);
}
