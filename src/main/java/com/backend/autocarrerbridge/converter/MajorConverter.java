package com.backend.autocarrerbridge.converter;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.backend.autocarrerbridge.dto.MajorDTO;
import com.backend.autocarrerbridge.entity.Major;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.util.enums.Status;

@Component
public class MajorConverter {

    public static Major convertToEntity(MajorDTO majorDTO) {

        Major major = Major.builder()
                .id(majorDTO.getId())
                .name(majorDTO.getName())
                .code(majorDTO.getCode())
                .numberStudent(majorDTO.getNumberStudent())
                .description(majorDTO.getDescription())
                .build();
        major.setStatus(majorDTO.getStatus() != null ? majorDTO.getStatus() : Status.ACTIVE);
        major.setCreatedAt(majorDTO.getCreatedAt() != null ? majorDTO.getCreatedAt() : LocalDateTime.now());
        major.setUpdatedAt(LocalDateTime.now());

        Section section = new Section();
        section.setId(majorDTO.getSectionId());
        major.setSection(section);

        return major;
    }

    public static MajorDTO convertToDTO(Major major) {
        return MajorDTO.builder()
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
