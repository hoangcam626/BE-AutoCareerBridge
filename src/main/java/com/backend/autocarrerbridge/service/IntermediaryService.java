package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.SubAdmin;
import com.backend.autocarrerbridge.entity.University;

public interface IntermediaryService {
    University findUniversityByEmail(String email);

    Business findBusinessByEmail(String email);

    SubAdmin findSubAdminByEmail(String email);
}
