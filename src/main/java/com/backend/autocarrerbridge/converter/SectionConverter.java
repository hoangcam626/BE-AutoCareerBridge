package com.backend.autocarrerbridge.converter;

import com.backend.autocarrerbridge.dto.response.section.SectionResponse;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.util.enums.Status;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component

public class SectionConverter {

  public static Section convertToEntity(SectionResponse sectionResponse){

    Section section = Section.builder()
        .id(sectionResponse.getId())
        .name(sectionResponse.getName())
        .description(sectionResponse.getDescription())
        .build();

    section.setStatus(sectionResponse.getStatus() != null ? sectionResponse.getStatus() : Status.ACTIVE);
    section.setCreatedAt(sectionResponse.getCreatedAt() != null ? sectionResponse.getCreatedAt() : LocalDateTime.now());
    section.setUpdatedAt(LocalDateTime.now());


    if (sectionResponse.getUniversityId() != null) {
      University university = new University();
      university.setId(sectionResponse.getUniversityId());
      section.setUniversity(university);
    }// tedAt

    return section;
  }
  public static SectionResponse convertToDTO(Section section){

    return SectionResponse.builder()
        .id(section.getId())
        .name(section.getName())
        .description(section.getDescription())
        .status(section.getStatus())
        .createdAt(section.getCreatedAt())
        .updatedAt(section.getUpdatedAt())
        .universityId(section.getUniversity().getId())
        .build();
  }
}