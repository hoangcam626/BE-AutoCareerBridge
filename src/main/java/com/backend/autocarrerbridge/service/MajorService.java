package com.backend.autocarrerbridge.service;

import java.util.List;

import com.backend.autocarrerbridge.dto.MajorDTO;

public interface MajorService {
    MajorDTO createMajor(MajorDTO majorDTO);

    MajorDTO updateMajor(int id, MajorDTO majorDTO);

    MajorDTO deleteMajor(int id);

    List<MajorDTO> getAllMajor();

    List<MajorDTO> getMajorById(int id);
}
