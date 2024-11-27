package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.account.UserUniversityRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRequest;
import com.backend.autocarrerbridge.dto.response.university.UniversityRegisterResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.entity.University;
import java.util.List;

public interface UniversityService {

  UniversityRegisterResponse registerUniversity(UserUniversityRequest userUniversityRequest);

  UniversityResponse update(int id, UniversityRequest universityRequest);

  List<UniversityResponse> getById(int id);

  List<UniversityResponse> getAll();

  University findById(Integer id);

}
