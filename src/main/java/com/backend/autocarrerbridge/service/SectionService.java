package com.backend.autocarrerbridge.service;

import java.util.List;

import com.backend.autocarrerbridge.dto.request.section.SectionRequest;

public interface SectionService {

    SectionRequest createSection(SectionRequest sectionRequest);

    SectionRequest updateSection(int id, SectionRequest sectionRequest);

    SectionRequest deleteSection(int id);

    List<SectionRequest> getAllSection();

    List<SectionRequest> getSectionById(int id);
}
