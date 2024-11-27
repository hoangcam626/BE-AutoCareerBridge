package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.converter.SectionConverter;
import com.backend.autocarrerbridge.dto.request.section.SectionRequest;
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
  public SectionRequest createSection(SectionRequest sectionRequest) {
    University university = universityRepository.findById(sectionRequest.getUniversityId())
        .orElseThrow(() -> new AppException(ErrorCode.ERROR_UNIVERSITY_NOT_FOUND));
    if (sectionRepository.findByName(sectionRequest.getName()) != null) {
      throw new AppException(ErrorCode.SECTION_EXISTED);
    }

    Section section = SectionConverter.convertToEntity(sectionRequest);
    section.setUniversity(university);
    Section saveSection = sectionRepository.save(section);
    return SectionConverter.convertToResponse(saveSection);
  }

  @Transactional
  @Override
  public SectionRequest updateSection(int id, SectionRequest sectionRequest) {
    Section section = sectionRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.SECTION_NOT_FOUND));
    section.setName(sectionRequest.getName());
    section.setDescription(sectionRequest.getDescription());
    section.setStatus(sectionRequest.getStatus());
    section.setUpdatedAt(LocalDateTime.now());
    Section updatedSection = sectionRepository.save(section);

    return SectionConverter.convertToResponse(updatedSection);
  }

  @Override
  public SectionRequest deleteSection(int id) {
    Section section = sectionRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.SECTION_NOT_FOUND));
    sectionRepository.delete(section);
    return SectionConverter.convertToResponse(section);
  }

  @Override
  public List<SectionRequest> getAllSection() {
    List<Section> sectionList = sectionRepository.findAll();
    sectionList.sort(Comparator.comparingLong(Section::getId).reversed());
    return sectionList.stream().map(SectionConverter::convertToResponse).toList();

  }

  @Override
  public List<SectionRequest> getSectionById(int id) {
    Section section = sectionRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.SECTION_NOT_FOUND));
    return List.of(SectionConverter.convertToResponse(section));
  }

}
