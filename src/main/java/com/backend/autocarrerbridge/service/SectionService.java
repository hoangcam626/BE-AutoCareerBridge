package com.backend.autocarrerbridge.service;

import java.util.List;

import com.backend.autocarrerbridge.dto.SectionDTO;

public interface SectionService {

    SectionDTO createSection(SectionDTO sectionDTO);

    SectionDTO updateSection(int id, SectionDTO sectionDTO);

    SectionDTO deleteSection(int id);

    List<SectionDTO> getAllSection();

    List<SectionDTO> getSectionById(int id);
}
