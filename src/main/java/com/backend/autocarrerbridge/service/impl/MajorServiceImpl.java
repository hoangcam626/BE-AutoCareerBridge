package com.backend.autocarrerbridge.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.converter.MajorConverter;
import com.backend.autocarrerbridge.dto.request.major.MajorRequest;
import com.backend.autocarrerbridge.entity.Major;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.MajorRepository;
import com.backend.autocarrerbridge.repository.SectionRepository;
import com.backend.autocarrerbridge.service.MajorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;

    private final SectionRepository sectionRepository;

    @Transactional
    @Override
    public MajorRequest createMajor(MajorRequest majorRequest) {
        Section section = sectionRepository
                .findById(majorRequest.getSectionId())
                .orElseThrow(() -> new AppException(ErrorCode.ERROR_SECTION_NOT_FOUND));
        if (majorRepository.findByName(majorRequest.getName()) != null) {
            throw new AppException(ErrorCode.ERROR_NAME);
        }

        Major major = MajorConverter.convertToEntity(majorRequest);
        major.setSection(section);
        Major saveMajor = majorRepository.save(major);
        return MajorConverter.convertToDTO(saveMajor);
    }

    @Transactional
    @Override
    public MajorRequest updateMajor(int id, MajorRequest majorRequest) {
        Major major = majorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUNDED));
        major.setCode(majorRequest.getCode());
        major.setName(majorRequest.getName());
        major.setStatus(majorRequest.getStatus());
        major.setNumberStudent(majorRequest.getNumberStudent());
        major.setDescription(majorRequest.getDescription());
        major.setUpdatedAt(LocalDateTime.now());
        Major updateMajor = majorRepository.save(major);

        return MajorConverter.convertToDTO(updateMajor);
    }

    @Override
    public MajorRequest deleteMajor(int id) {
        Major major = majorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUNDED));
        majorRepository.delete(major);
        return MajorConverter.convertToDTO(major);
    }

    @Override
    public List<MajorRequest> getAllMajor() {
        List<Major> majors = majorRepository.findAll();
        majors.sort(Comparator.comparingLong(Major::getId).reversed());
        return majors.stream().map(MajorConverter::convertToDTO).toList();
    }

    @Override
    public List<MajorRequest> getMajorById(int id) {
        Major major = majorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUNDED));
        return List.of(MajorConverter.convertToDTO(major));
    }
}
