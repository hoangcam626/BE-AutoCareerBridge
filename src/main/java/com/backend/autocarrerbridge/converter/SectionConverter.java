package com.backend.autocarrerbridge.converter;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.backend.autocarrerbridge.dto.request.section.SectionRequest;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.util.enums.Status;

@Component
public class SectionConverter {

    public static Section convertToEntity(SectionRequest sectionRequest) {

        Section section = Section.builder()
                .id(sectionRequest.getId())
                .name(sectionRequest.getName())
                .description(sectionRequest.getDescription())
                .build();

        section.setStatus(sectionRequest.getStatus() != null ? sectionRequest.getStatus() : Status.ACTIVE);
        section.setCreatedAt(
                sectionRequest.getCreatedAt() != null ? sectionRequest.getCreatedAt() : LocalDateTime.now());
        section.setUpdatedAt(LocalDateTime.now());

        if (sectionRequest.getUniversityId() != null) {
            University university = new University();
            university.setId(sectionRequest.getUniversityId());
            section.setUniversity(university);
        } // tedAt

        return section;
    }

    public static SectionRequest convertToDTO(Section section) {

        return SectionRequest.builder()
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
