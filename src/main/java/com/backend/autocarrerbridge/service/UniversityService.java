package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.AccountRespone.DisplayUniverSityDTO;
import com.backend.autocarrerbridge.dto.AccountRespone.UserUniversityDTO;
import com.backend.autocarrerbridge.entity.University;

public interface UniversityService {
    DisplayUniverSityDTO registerUniversity(UserUniversityDTO userUniversityDTO);

    University findByEmail(String email);
}
