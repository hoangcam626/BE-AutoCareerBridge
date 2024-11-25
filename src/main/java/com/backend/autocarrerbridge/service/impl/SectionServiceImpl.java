package com.backend.autocarrerbridge.service.impl;
import com.backend.autocarrerbridge.converter.SectionConverter;
import com.backend.autocarrerbridge.dto.response.section.SectionResponse;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.repository.SectionRepository;
import com.backend.autocarrerbridge.repository.UniversityRepository;
import com.backend.autocarrerbridge.service.SectionService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class SectionServiceImpl implements SectionService {


  private final SectionRepository sectionRepository;

  private final UniversityRepository universityRepository;


  @Transactional
  @Override
  public SectionResponse createSection(SectionResponse sectionResponse) {
    University university = universityRepository.findById(sectionResponse.getUniversityId())
        .orElseThrow(() -> new AppException(ErrorCode.ERROR_UNIVERSITY_NOT_FOUND));
    if (sectionRepository.findByName(sectionResponse.getName()) != null) {
      throw new AppException(ErrorCode.SECTION_EXISTED);
    }

    Section section = SectionConverter.convertToEntity(sectionResponse);
    section.setUniversity(university);
    Section saveSection = sectionRepository.save(section);
    return SectionConverter.convertToDTO(saveSection);
  }

  @Transactional
  @Override
  public SectionResponse updateSection(int id, SectionResponse sectionResponse) {
    Section section = sectionRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.SECTION_NOT_FOUND));
    section.setName(sectionResponse.getName());
    section.setDescription(sectionResponse.getDescription());
    section.setStatus(sectionResponse.getStatus());
    section.setUpdatedAt(LocalDateTime.now());
    Section updatedSection = sectionRepository.save(section);

    return SectionConverter.convertToDTO(updatedSection);
  }

  @Override
  public SectionResponse deleteSection(int id) {
    Section section = sectionRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.SECTION_NOT_FOUND));
    sectionRepository.delete(section);
    return SectionConverter.convertToDTO(section);
  }

  @Override
  public List<SectionResponse> getAllSection() {
    List<Section> sectionList = sectionRepository.findAll();
    sectionList.sort(Comparator.comparingLong(Section::getId).reversed());
    return sectionList.stream().map(SectionConverter::convertToDTO)
        .toList();

  }

  @Override
  public List<SectionResponse> getSectionById(int id) {
    Section section = sectionRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.SECTION_NOT_FOUND));
    return List.of(SectionConverter.convertToDTO(section));
  }
}
