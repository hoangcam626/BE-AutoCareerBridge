package com.backend.autocarrerbridge.converter;

import static com.backend.autocarrerbridge.util.Constant.SUB;

import com.backend.autocarrerbridge.dto.request.section.SectionRequest;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.impl.TokenServiceImpl;
import com.backend.autocarrerbridge.util.enums.Status;
import java.text.ParseException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class SectionConverter {

  private static TokenServiceImpl tokenService;

  // Constructor được sử dụng để inject TokenServiceImpl vào trong SectionConverter
  @Autowired
  public SectionConverter(TokenServiceImpl tokenService) {
    SectionConverter.tokenService = tokenService;
  }

  // Phương thức chuyển đổi SectionRequest thành Section entity
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
    section.setCreatedAt(sectionRequest.getCreatedAt() != null ? sectionRequest.getCreatedAt() : LocalDateTime.now());
    // Cập nhật thời gian chỉnh sửa là thời gian hiện tại
    section.setUpdatedAt(LocalDateTime.now());
    // Lấy thông tin người dùng từ token và gán vào trường createdBy và updatedBy
    try {
      String currentUser = tokenService.getClaim(tokenService.getJWT(), SUB);
      section.setCreatedBy(currentUser);
      section.setUpdatedBy(currentUser);
    } catch (ParseException e) {

      throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
    }

    if (sectionRequest.getUniversityId() != null) {
      University university = new University();
      university.setId(sectionRequest.getUniversityId());
      section.setUniversity(university);
    }// tedAt

    return section;
  }

  // Phương thức chuyển đổi Section entity thành SectionRequest (dùng để trả về response)
  public static SectionRequest convertToResponse(Section section) {

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
        .build();
  }
}