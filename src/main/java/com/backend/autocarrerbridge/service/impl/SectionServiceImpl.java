package com.backend.autocarrerbridge.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.converter.SectionConverter;
import com.backend.autocarrerbridge.dto.SectionDTO;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.SectionRepository;
import com.backend.autocarrerbridge.repository.UniversityRepository;
import com.backend.autocarrerbridge.service.SectionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;

    private final UniversityRepository universityRepository;

    @Transactional
    @Override
    public SectionDTO createSection(SectionDTO sectionDTO) {
        University university = universityRepository
                .findById(sectionDTO.getUniversityId())
                .orElseThrow(() -> new AppException(ErrorCode.ERROR_UNIVERSITY_NOT_FOUND));
        if (sectionRepository.findByName(sectionDTO.getName()) != null) {
            throw new AppException(ErrorCode.ERROR_NAME);
        }

        Section section = SectionConverter.convertToEntity(sectionDTO);
        section.setUniversity(university);
        Section saveSection = sectionRepository.save(section);
        return SectionConverter.convertToDTO(saveSection);
    }

    @Transactional
    @Override
    public SectionDTO updateSection(int id, SectionDTO sectionDTO) {
        Section section = sectionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUNDED));
        section.setName(sectionDTO.getName());
        section.setDescription(sectionDTO.getDescription());
        section.setStatus(sectionDTO.getStatus());
        section.setUpdatedAt(LocalDateTime.now());
        Section updatedSection = sectionRepository.save(section);

        return SectionConverter.convertToDTO(updatedSection);
    }

    @Override
    public SectionDTO deleteSection(int id) {
        Section section = sectionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUNDED));
        sectionRepository.delete(section);
        return SectionConverter.convertToDTO(section);
    }

    @Override
    public List<SectionDTO> getAllSection() {
        List<Section> sectionList = sectionRepository.findAll();
        sectionList.sort(Comparator.comparingLong(Section::getId).reversed());
        return sectionList.stream().map(SectionConverter::convertToDTO).toList();
    }

    @Override
    public List<SectionDTO> getSectionById(int id) {
        Section section = sectionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUNDED));
        return List.of(SectionConverter.convertToDTO(section));
    }
}
