package com.backend.autocarrerbridge.service;


import java.util.List;

import com.backend.autocarrerbridge.dto.request.section.SectionRequest;
import java.util.Map;

public interface SectionService {

  SectionRequest createSection(SectionRequest sectionRequest);

  SectionRequest updateSection(int id, SectionRequest sectionRequest);

  List<SectionRequest> deleteSection(List<Integer> ids);

  List<SectionRequest> getAllSection(Integer universityId);

  List<SectionRequest> getSectionById(int id);

  SectionRequest setSectionInactive(int id);

  SectionRequest setSectionActive(int id);

  long countSection(Integer universityId);

  Map<String, Long> countMajorsInSections();
}
