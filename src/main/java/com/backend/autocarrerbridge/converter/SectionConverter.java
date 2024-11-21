package com.backend.autocarrerbridge.converter;

import com.backend.autocarrerbridge.dto.SectionDTO;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.repository.UniversityRepository;
import com.backend.autocarrerbridge.util.enums.Status;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SectionConverter {
  @Autowired
  UniversityRepository universityRepository;

  public static Section convertToEntity(SectionDTO sectionDTO){

    Section section = Section.builder()
        .id(sectionDTO.getId())
        .name(sectionDTO.getName())
        .description(sectionDTO.getDescription())
        .build();

    section.setStatus(sectionDTO.getStatus() != null ? sectionDTO.getStatus() : Status.ACTIVE);
    section.setCreatedBy(sectionDTO.getCreatedBy() != null ? sectionDTO.getCreatedBy() : 1);
    section.setUpdatedBy(sectionDTO.getUpdatedBy() != null ? sectionDTO.getUpdatedBy() : 1);
    section.setCreatedAt(sectionDTO.getCreatedAt() != null ? sectionDTO.getCreatedAt() : LocalDateTime.now());
    section.setUpdatedAt(LocalDateTime.now());


    if (sectionDTO.getUniversityId() != null) {
      University university = new University();
      university.setId(sectionDTO.getUniversityId());
      section.setUniversity(university);
    }// tedAt

    return section;
  }
  public static SectionDTO convertToDTO(Section section){

    return SectionDTO.builder()
        .id(section.getId())
        .name(section.getName())
        .description(section.getDescription())
        .status(section.getStatus())
        .createdAt(section.getCreatedAt())
        .updatedAt(section.getUpdatedAt())
        .createdBy(section.getCreatedBy())
        .updatedBy(section.getUpdatedBy())
        .universityId(section.getUniversity().getId())
        .build();
  }
}