package com.backend.autocarrerbridge.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.converter.SectionConverter;
import com.backend.autocarrerbridge.dto.request.section.SectionRequest;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.controller.repository.SectionRepository;
import com.backend.autocarrerbridge.controller.repository.UniversityRepository;
import com.backend.autocarrerbridge.service.SectionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final UniversityRepository universityRepository;

    /**
     * Tạo mới một section dựa trên SectionRequest.
     * @param sectionRequest Đối tượng chứa thông tin của section cần tạo.
     * @return Đối tượng SectionRequest đã được tạo.
     */
    @Transactional
    @Override
    public SectionRequest createSection(SectionRequest sectionRequest) {
        // Tìm university dựa trên universityId trong sectionRequest
        University university = universityRepository
            .findById(sectionRequest.getUniversityId())
            .orElseThrow(() -> new AppException(ErrorCode.ERROR_UNIVERSITY_NOT_FOUND));

        // Kiểm tra xem section với tên này đã tồn tại chưa
        if (sectionRepository.findByName(sectionRequest.getName()) != null) {
            throw new AppException(ErrorCode.SECTION_EXISTED);
        }

        // Chuyển đổi SectionRequest thành Section entity
        Section section = SectionConverter.convertToEntity(sectionRequest);
        section.setUniversity(university);

        // Lưu section vào cơ sở dữ liệu
        Section saveSection = sectionRepository.save(section);

        // Chuyển đổi Section entity thành SectionRequest để trả về
        return SectionConverter.convertToResponse(saveSection);
    }

    /**
     * Cập nhật thông tin của một section.
     * @param id ID của section cần cập nhật.
     * @param sectionRequest Đối tượng chứa thông tin mới của section.
     * @return Đối tượng SectionRequest đã được cập nhật.
     */
    @Transactional
    @Override
    public SectionRequest updateSection(int id, SectionRequest sectionRequest) {
        // Tìm section dựa trên ID
        Section section = sectionRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.SECTION_NOT_FOUND));

        // Cập nhật các trường thông tin của section
        section.setName(sectionRequest.getName());
        section.setDescription(sectionRequest.getDescription());
        section.setStatus(sectionRequest.getStatus());
        section.setUpdatedAt(LocalDateTime.now());

        // Lưu section đã được cập nhật vào cơ sở dữ liệu
        Section updatedSection = sectionRepository.save(section);

        // Chuyển đổi Section entity thành SectionRequest để trả về
        return SectionConverter.convertToResponse(updatedSection);
    }

    /**
     * Xóa một section dựa trên ID.
     * @param id ID của section cần xóa.
     * @return Đối tượng SectionRequest đã bị xóa.
     */
    @Override
    public SectionRequest deleteSection(int id) {
        // Tìm section dựa trên ID
        Section section = sectionRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.SECTION_NOT_FOUND));

        // Kiểm tra xem section có chứa major nào không
        if (!section.getMajors().isEmpty()) {
            throw new AppException(ErrorCode.SECTION_HAVE_MAJOR);
        }

        // Xóa section khỏi cơ sở dữ liệu
        sectionRepository.delete(section);

        // Chuyển đổi Section entity thành SectionRequest để trả về
        return SectionConverter.convertToResponse(section);
    }

    /**
     * Lấy danh sách tất cả các section.
     * @return Danh sách các SectionRequest.
     */
    @Override
    public List<SectionRequest> getAllSection() {
        // Tìm tất cả các section từ cơ sở dữ liệu
        List<Section> sectionList = sectionRepository.findAll();

        // Sắp xếp danh sách section theo thứ tự giảm dần của ID
        sectionList.sort(Comparator.comparingLong(Section::getId).reversed());

        // Chuyển đổi từng section thành SectionRequest và trả về danh sách
        return sectionList.stream().map(SectionConverter::convertToResponse).toList();
    }

    /**
     * Lấy thông tin của một section dựa trên ID.
     * @param id ID của section cần lấy thông tin.
     * @return Danh sách chứa SectionRequest tương ứng với ID đã cho.
     */
    @Override
    public List<SectionRequest> getSectionById(int id) {
        // Tìm section dựa trên ID
        Section section = sectionRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.SECTION_NOT_FOUND));

        // Chuyển đổi Section entity thành SectionRequest và trả về dưới dạng danh sách
        return List.of(SectionConverter.convertToResponse(section));
    }
}
