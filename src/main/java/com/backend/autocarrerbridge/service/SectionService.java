package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.section.SectionRequest;
import com.backend.autocarrerbridge.entity.Major;
import java.util.List;


public interface SectionService {

  SectionRequest createSection(SectionRequest sectionRequest);

  SectionRequest updateSection(int id, SectionRequest sectionRequest);

  SectionRequest deleteSection(int id);

  List<SectionRequest> getAllSection();

  List<SectionRequest> getSectionById(int id);

}
