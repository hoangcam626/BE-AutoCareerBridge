package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.response.section.SectionResponse;
import java.util.List;

public interface SectionService {

  SectionResponse createSection(SectionResponse sectionResponse);

  SectionResponse updateSection(int id, SectionResponse sectionResponse);

  SectionResponse deleteSection(int id);

  List<SectionResponse> getAllSection();

  List<SectionResponse> getSectionById(int id);
}
