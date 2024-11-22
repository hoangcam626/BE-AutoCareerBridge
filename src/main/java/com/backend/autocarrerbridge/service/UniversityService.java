package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.accountresponse.DisplayUniverSityDTO;
import com.backend.autocarrerbridge.dto.accountresponse.UserUniversityDTO;
import com.backend.autocarrerbridge.entity.University;


public interface UniversityService {
    DisplayUniverSityDTO registerUniversity(UserUniversityDTO userUniversityDTO);


}
