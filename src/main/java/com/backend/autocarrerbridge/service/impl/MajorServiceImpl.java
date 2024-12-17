package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.util.enums.Status;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import jakarta.transaction.Transactional;

import java.util.stream.Collectors;
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

  /**
   * Tạo mới một major dựa trên MajorRequest.
   *
   * @param majorRequest Đối tượng chứa thông tin của major cần tạo.
   * @return Đối tượng MajorRequest đã được tạo.
   */
  @Transactional
  @Override
  public MajorRequest createMajor(MajorRequest majorRequest) {
    // Tìm section dựa trên sectionId trong majorRequest
    Section section = sectionRepository
        .findById(majorRequest.getSectionId())
        .orElseThrow(() -> new AppException(ErrorCode.ERROR_SECTION_NOT_FOUND));

    // Kiểm tra xem major với tên này đã tồn tại chưa
    List<Major> existingMajorByName = majorRepository.findByName(majorRequest.getName());
    if (!existingMajorByName.isEmpty()) {
      throw new AppException(ErrorCode.ERROR_NAME);
    }

    // Kiểm tra xem mã chuyên ngành (code) đã tồn tại chưa
    List<Major> existingMajorByCode = majorRepository.findByCode(majorRequest.getCode());
    if (!existingMajorByCode.isEmpty()) {
      throw new AppException(ErrorCode.ERROR_EXIST_CODE);
    }

    // Chuyển đổi MajorRequest thành Major entity
    Major major = MajorConverter.convertToEntity(majorRequest);
    major.setSection(section);

    // Lưu major vào cơ sở dữ liệu
    Major saveMajor = majorRepository.save(major);

    // Chuyển đổi Major entity thành MajorRequest để trả về
    return MajorConverter.convertToDTO(saveMajor);
  }

  /**
   * Cập nhật thông tin của một major.
   *
   * @param id           ID của major cần cập nhật.
   * @param majorRequest Đối tượng chứa thông tin mới của major.
   * @return Đối tượng MajorRequest đã được cập nhật.
   */
  @Transactional
  @Override
  public MajorRequest updateMajor(int id, MajorRequest majorRequest) {
    // Tìm major dựa trên ID
    Major major = majorRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUNDED));

    // Kiểm tra xem major với tên này đã tồn tại chưa và ID khác với ID hiện tại
    List<Major> existingMajorByName = majorRepository.findByName(majorRequest.getName());
    if (existingMajorByName.stream().anyMatch(existingMajor -> existingMajor.getId() != id)) {
      throw new AppException(ErrorCode.ERROR_NAME);
    }

    // Kiểm tra xem mã chuyên ngành (code) đã tồn tại chưa và ID khác với ID hiện tại
    List<Major> existingMajorByCode = majorRepository.findByCode(majorRequest.getCode());
    if (existingMajorByCode.stream().anyMatch(existingMajor -> existingMajor.getId() != id)) {
      throw new AppException(ErrorCode.ERROR_EXIST_CODE);
    }

    // Cập nhật các trường thông tin của major
    major.setCode(majorRequest.getCode());
    major.setName(majorRequest.getName());
    major.setStatus(majorRequest.getStatus());
    major.setNumberStudent(majorRequest.getNumberStudent());
    major.setDescription(majorRequest.getDescription());
    major.setUpdatedAt(LocalDateTime.now());

    // Lưu major đã được cập nhật vào cơ sở dữ liệu
    Major updateMajor = majorRepository.save(major);

    // Chuyển đổi Major entity thành MajorRequest để trả về
    return MajorConverter.convertToDTO(updateMajor);
  }

  /**
   * Xóa một major dựa trên ID.
   *
   * @param ids ID của major cần xóa.
   * @return Đối tượng MajorRequest đã bị xóa.
   */
  @Override
  public List<MajorRequest> deleteMajor(List<Integer> ids) {
    List<Major> majorList = ids.stream().map(id -> majorRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUNDED))).toList();
    // Xóa major khỏi cơ sở dữ liệu
    majorRepository.deleteAll(majorList);

    // Chuyển đổi Major entity thành MajorRequest để trả về
    return majorList.stream().map(MajorConverter::convertToDTO).collect(Collectors.toList());
  }

  /**
   * Lấy danh sách tất cả các major.
   *
   * @return Danh sách các MajorRequest.
   */
  @Override
  public List<MajorRequest> getAllMajor() {
    // Tìm tất cả các major từ cơ sở dữ liệu
    List<Major> majors = majorRepository.findAll();

    // Sắp xếp danh sách major theo thứ tự giảm dần của ID
    majors.sort(Comparator.comparingLong(Major::getId).reversed());

    // Chuyển đổi từng major thành MajorRequest và trả về danh sách
    return majors.stream().map(MajorConverter::convertToDTO).toList();
  }

  /**
   * Lấy thông tin của một major dựa trên ID.
   *
   * @param id ID của major cần lấy thông tin.
   * @return Danh sách chứa MajorRequest tương ứng với ID đã cho.
   */
  @Override
  public List<MajorRequest> getMajorById(int id) {
    // Tìm major dựa trên ID
    Major major = majorRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUNDED));

    // Chuyển đổi Major entity thành MajorRequest và trả về dưới dạng danh sách
    return List.of(MajorConverter.convertToDTO(major));
  }

  @Override
  public MajorRequest setMajorInactive(int id) {
    Major major = majorRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUNDED));
    major.setStatus(Status.INACTIVE);
    major.setUpdatedAt(LocalDateTime.now());
    majorRepository.save(major);
    return MajorConverter.convertToDTO(major);
  }

  @Override
  public MajorRequest setMajorActive(int id) {
    Major major = majorRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUNDED));
    major.setStatus(Status.ACTIVE);
    major.setUpdatedAt(LocalDateTime.now());
    majorRepository.save(major);
    return MajorConverter.convertToDTO(major);
  }
}
