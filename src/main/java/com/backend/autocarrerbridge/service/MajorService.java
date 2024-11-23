package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.MajorDTO;
import java.util.List;


public interface MajorService {
  MajorDTO createMajor(MajorDTO majorDTO);

  MajorDTO updateMajor(int id, MajorDTO majorDTO);

  MajorDTO deleteMajor(int id);

  List<MajorDTO> getAllMajor();

  List<MajorDTO> getMajorById(int id);
}
