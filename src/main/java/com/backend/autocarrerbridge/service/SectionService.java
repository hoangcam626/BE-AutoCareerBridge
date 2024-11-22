package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.SectionDTO;
import java.util.List;

public interface SectionService {

  SectionDTO createSection(SectionDTO sectionDTO);

  SectionDTO updateSection(int id, SectionDTO sectionDTO);

  SectionDTO deleteSection(int id);

  List<SectionDTO> getAllSection();

  List<SectionDTO> getSectionById(int id);
}
