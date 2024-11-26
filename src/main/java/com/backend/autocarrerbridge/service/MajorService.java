package com.backend.autocarrerbridge.service;

import java.util.List;

import com.backend.autocarrerbridge.dto.request.major.MajorRequest;

public interface MajorService {
    MajorRequest createMajor(MajorRequest majorRequest);

    MajorRequest updateMajor(int id, MajorRequest majorRequest);

    MajorRequest deleteMajor(int id);

    List<MajorRequest> getAllMajor();

    List<MajorRequest> getMajorById(int id);
}
