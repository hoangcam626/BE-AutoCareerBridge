package com.backend.autocarrerbridge.converter;

import static com.backend.autocarrerbridge.util.Constant.SUB;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.autocarrerbridge.dto.request.major.MajorRequest;
import com.backend.autocarrerbridge.dto.request.section.SectionRequest;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.service.impl.TokenServiceImpl;
import com.backend.autocarrerbridge.util.enums.Status;

@Component
public class SectionConverter {

    // TokenServiceImpl được sử dụng để lấy thông tin từ JWT
    private static TokenServiceImpl tokenService;

    // Constructor được sử dụng để inject TokenServiceImpl vào trong SectionConverter
    @Autowired
    public SectionConverter(TokenServiceImpl tokenService) {
        SectionConverter.tokenService = tokenService;
    }

    /**
     * Chuyển đổi từ SectionRequest sang Section entity.
     *
     * @param sectionRequest Đối tượng SectionRequest cần chuyển đổi.
     * @return Đối tượng Section entity đã chuyển đổi.
     */
    public static Section convertToEntity(SectionRequest sectionRequest) {
        // Tạo một đối tượng Section mới từ sectionRequest
        Section section = Section.builder()
                .id(sectionRequest.getId())
                .name(sectionRequest.getName())
                .description(sectionRequest.getDescription())
                .build();
        // Nếu không có status trong request, mặc định là ACTIVE
        section.setStatus(sectionRequest.getStatus() != null ? sectionRequest.getStatus() : Status.ACTIVE);
        // Nếu không có thời gian tạo, lấy thời gian hiện tại
        section.setCreatedAt(
                sectionRequest.getCreatedAt() != null ? sectionRequest.getCreatedAt() : LocalDateTime.now());
        // Cập nhật thời gian chỉnh sửa là thời gian hiện tại
        section.setUpdatedAt(LocalDateTime.now());
        // Lấy thông tin người dùng từ token và gán vào trường createdBy và updatedBy
        try {
            String currentUser = tokenService.getClaim(tokenService.getJWT(), SUB);
            section.setCreatedBy(currentUser);
            section.setUpdatedBy(currentUser);
        } catch (ParseException e) {
            // Ném ra AppException nếu token không hợp lệ
            throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
        }

        // Nếu universityId không null, tạo và gán đối tượng University
        if (sectionRequest.getUniversityId() != null) {
            University university = new University();
            university.setId(sectionRequest.getUniversityId());
            section.setUniversity(university);
        }

        return section;
    }

    /**
     * Chuyển đổi từ Section entity sang SectionRequest (dùng để trả về response).
     *
     * @param section Đối tượng Section entity cần chuyển đổi.
     * @return Đối tượng SectionRequest đã chuyển đổi.
     */
    public static SectionRequest convertToResponse(Section section) {
        // Lấy danh sách major và chuyển đổi từng phần tử sang MajorRequest
        List<MajorRequest> majorRequestList = section.getMajors() != null
                ? section.getMajors().stream()
                        .map(major -> MajorRequest.builder()
                                .id(major.getId())
                                .code(major.getCode())
                                .name(major.getName())
                                .status(major.getStatus())
                                .numberStudent(major.getNumberStudent())
                                .description(major.getDescription())
                                .sectionId(major.getId())
                                .createdAt(major.getCreatedAt())
                                .updatedAt(major.getUpdatedAt())
                                .createdBy(major.getCreatedBy())
                                .updatedBy(major.getUpdatedBy())
                                .build())
                        .toList()
                : List.of();

        return SectionRequest.builder()
                .id(section.getId())
                .name(section.getName())
                .description(section.getDescription())
                .status(section.getStatus())
                .createdAt(section.getCreatedAt())
                .updatedAt(section.getUpdatedAt())
                .createdBy(section.getCreatedBy())
                .updatedBy(section.getUpdatedBy())
                .universityId(section.getUniversity().getId())
                .majorList(majorRequestList)
                .build();
    }
}
