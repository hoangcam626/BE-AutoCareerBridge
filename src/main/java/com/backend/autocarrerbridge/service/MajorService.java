package com.backend.autocarrerbridge.service;

import java.util.List;

import com.backend.autocarrerbridge.dto.request.major.MajorRequest;
import java.util.Map;

public interface MajorService {

  MajorRequest createMajor(MajorRequest majorRequest);

  MajorRequest updateMajor(int id, MajorRequest majorRequest);

  List<MajorRequest> deleteMajor(List<Integer> ids);

  List<MajorRequest> getAllMajor(Integer universityId);

  List<MajorRequest> getMajorById(int id);

  MajorRequest setMajorInactive(int id);

  MajorRequest setMajorActive(int id);

  long countMajor(Integer universityId);

  int getTotalNumberStudents(Integer universityId);

  Map<String, Integer> getNumberStudentsInMajor(Integer universityId);
}
