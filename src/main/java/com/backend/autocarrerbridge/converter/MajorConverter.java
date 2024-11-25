package com.backend.autocarrerbridge.converter;

import com.backend.autocarrerbridge.dto.request.major.MajorRequest;
import com.backend.autocarrerbridge.entity.Major;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.util.enums.Status;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class MajorConverter {

  public static Major convertToEntity(MajorRequest majorRequest) {

    Major major = Major.builder()
        .id(majorRequest.getId())
        .name(majorRequest.getName())
        .code(majorRequest.getCode())
        .numberStudent(majorRequest.getNumberStudent())
        .description(majorRequest.getDescription())
        .build();
    major.setStatus(majorRequest.getStatus() != null ? majorRequest.getStatus() : Status.ACTIVE);
    major.setCreatedAt(majorRequest.getCreatedAt() != null ? majorRequest.getCreatedAt() : LocalDateTime.now());
    major.setUpdatedAt(LocalDateTime.now());

    Section section = new Section();
    section.setId(majorRequest.getSectionId());
    major.setSection(section);

    return major;
  }

  public static MajorRequest convertToDTO(Major major) {
    return MajorRequest.builder()
        .id(major.getId())
        .name(major.getName())
        .code(major.getCode())
        .numberStudent(major.getNumberStudent())
        .description(major.getDescription())
        .status(major.getStatus())
        .createdAt(major.getCreatedAt())
        .updatedAt(major.getUpdatedAt())
        .sectionId(major.getSection().getId())
        .build();
  }
}