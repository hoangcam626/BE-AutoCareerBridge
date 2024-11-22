package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.converter.MajorConverter;
import com.backend.autocarrerbridge.dto.MajorDTO;
import com.backend.autocarrerbridge.entity.Major;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.MajorRepository;
import com.backend.autocarrerbridge.repository.SectionRepository;
import com.backend.autocarrerbridge.service.MajorService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MajorServiceImpl implements MajorService {

  @Autowired
  private MajorRepository majorRepository;

  @Autowired
  private SectionRepository sectionRepository;


  @Transactional
  @Override
  public MajorDTO createMajor(MajorDTO majorDTO) {
    Section section = sectionRepository.findById(majorDTO.getSectionId())
        .orElseThrow(() -> new AppException("Không tìm thấy khoa",
            ErrorCode.ERROR_SECTION_NOT_FOUND));
    if (majorRepository.findByName(majorDTO.getName()) != null) {
      throw new AppException("Tên khoa đã tồn tại", ErrorCode.ERROR_NAME);
    }

    Major major = MajorConverter.convertToEntity(majorDTO);
    major.setSection(section);
    Major saveMajor = majorRepository.save(major);
    return MajorConverter.convertToDTO(saveMajor);
  }

  @Transactional
  @Override
  public MajorDTO updateMajor(int id, MajorDTO majorDTO) {
    Major major = majorRepository.findById(id)
        .orElseThrow(() -> new AppException("Chuyên ngành không tồn tại", ErrorCode.NOT_FOUND));
    major.setCode(majorDTO.getCode());
    major.setName(majorDTO.getName());
    major.setStatus(majorDTO.getStatus());
    major.setNumberStudent(majorDTO.getNumberStudent());
    major.setDescription(majorDTO.getDescription());
    major.setUpdatedAt(LocalDateTime.now());
    Major updateMajor = majorRepository.save(major);

    return MajorConverter.convertToDTO(updateMajor);
  }


  @Override
  public MajorDTO deleteMajor(int id) {
    Major major = majorRepository.findById(id)
        .orElseThrow(() -> new AppException("Chuyên ngành không tồn tại", ErrorCode.NOT_FOUND));
    majorRepository.delete(major);
    return MajorConverter.convertToDTO(major);
  }

  @Override
  public List<MajorDTO> getAllMajor() {
    List<Major> majors = majorRepository.findAll();
    majors.sort(Comparator.comparingLong(Major::getId).reversed());
    return majors.stream().map(MajorConverter::convertToDTO).collect(Collectors.toList());
  }

  @Override
  public List<MajorDTO> getMajorById(int id) {
    Major major = majorRepository.findById(id)
        .orElseThrow(() -> new AppException("Chuyên ngành không tồn tại", ErrorCode.NOT_FOUND));
    return List.of(MajorConverter.convertToDTO(major));
  }
}
