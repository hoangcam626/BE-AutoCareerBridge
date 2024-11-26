package com.backend.autocarrerbridge.converter;

import static com.backend.autocarrerbridge.util.Constant.SUB;

import com.backend.autocarrerbridge.dto.request.major.MajorRequest;
import com.backend.autocarrerbridge.entity.Major;
import com.backend.autocarrerbridge.entity.Section;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.service.impl.TokenServiceImpl;
import com.backend.autocarrerbridge.util.enums.Status;
import java.text.ParseException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MajorConverter {

  private static TokenServiceImpl tokenService;

  // Constructor được sử dụng để inject TokenServiceImpl vào trong MajorConverter
  @Autowired
  public MajorConverter(TokenServiceImpl tokenService) {
    MajorConverter.tokenService = tokenService;
  }
  private MajorConverter() {
    // Constructor private ngăn việc tạo đối tượng trực tiếp từ ngoài
  }

  public static Major convertToEntity(MajorRequest majorRequest) {

    Major major = Major.builder()
        .id(majorRequest.getId())
        .name(majorRequest.getName())
        .code(majorRequest.getCode())
        .numberStudent(majorRequest.getNumberStudent())
        .description(majorRequest.getDescription())
        .build();
    major.setStatus(majorRequest.getStatus() != null ? majorRequest.getStatus() : Status.ACTIVE);
    major.setCreatedAt(majorRequest.getCreatedAt() != null ? majorRequest.getCreatedAt() : LocalDateTime.now());
    major.setUpdatedAt(LocalDateTime.now());

    try {
      String currentUser = tokenService.getClaim(tokenService.getJWT(), SUB);
      major.setCreatedBy(currentUser);
      major.setUpdatedBy(currentUser);
    } catch (ParseException e) {

      throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
    }

    Section section = new Section();
    section.setId(majorRequest.getSectionId());
    major.setSection(section);

    return major;
  }

  public static MajorRequest convertToDTO(Major major) {
    return MajorRequest.builder()
        .id(major.getId())
        .name(major.getName())
        .code(major.getCode())
        .numberStudent(major.getNumberStudent())
        .description(major.getDescription())
        .status(major.getStatus())
        .createdAt(major.getCreatedAt())
        .updatedAt(major.getUpdatedAt())
        .createdBy(major.getCreatedBy())
        .updatedBy(major.getUpdatedBy())
        .sectionId(major.getSection().getId())
        .build();
  }
}
