package com.backend.autocarrerbridge.converter;
import com.backend.autocarrerbridge.dto.request.instructional.InstructionalRequest;
import com.backend.autocarrerbridge.dto.response.instructional.InstructionalResponse;
import com.backend.autocarrerbridge.entity.Instructional;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstructionalConverter {

  private static TokenServiceImpl tokenService;

  @Autowired
  public InstructionalConverter(TokenServiceImpl tokenService) {
    InstructionalConverter.tokenService = tokenService;
  }

  /**
   * Chuyển đổi từ InstructionalRequest sang Instructional entity.
   *
   * @param request Đối tượng InstructionalRequest cần chuyển đổi.
   * @return Đối tượng Instructional entity đã chuyển đổi.
   */
  public static Instructional toEntity(InstructionalRequest request) {
    if (request == null) {
      return null;
    }

    return Instructional.builder()
        .id(request.getId())
        .name(request.getName())
        .gender(request.getGender())
        .email(request.getEmail())
        .address(request.getAddress())
        .instructionalCode(request.getInstructionalCode())
        .phone(request.getPhone())
        .university(University.builder().id(request.getUniversityId()).build())
        .userAccount(UserAccount.builder().id(request.getUserAccountId()).build())
        .build();

  }

  /**
   * Chuyển đổi từ Instructional entity sang InstructionalResponse.
   *
   * @param instructional Đối tượng Instructional entity cần chuyển đổi.
   * @return Đối tượng InstructionalResponse đã chuyển đổi.
   */
  public static InstructionalResponse toResponse(Instructional instructional) {
    if (instructional == null) {
      return null;
    }
   return InstructionalResponse.builder()
        .id(instructional.getId())
        .name(instructional.getName())
        .gender(instructional.getGender())
        .dateOfBirth(instructional.getDateOfBirth())
        .email(instructional.getEmail())
        .address(instructional.getAddress())
        .instructionalCode(instructional.getInstructionalCode())
        .instructionalImageId(instructional.getInstructionalImageId())
        .phone(instructional.getPhone())
        .status(instructional.getStatus())
        .createdAt(instructional.getCreatedAt())
        .updatedAt(instructional.getUpdatedAt())
        .createdBy(instructional.getCreatedBy())
        .updatedBy(instructional.getUpdatedBy())
        .universityId(instructional.getUniversity().getId())
        .userAccountId(instructional.getUserAccount().getId())
        .build();
  }

  /**
   * Cập nhật Instructional entity từ InstructionalRequest.
   *
   * @param request       Đối tượng InstructionalRequest chứa dữ liệu cập nhật.
   * @param instructional Đối tượng Instructional entity cần được cập nhật.
   */
  public void updateEntityFormRequest(InstructionalRequest request, Instructional instructional) {
    if (request == null || instructional == null) {
      return;
    }
    instructional.setName(request.getName());
    instructional.setGender(request.getGender());
    instructional.setDateOfBirth(request.getDateOfBirth());
    instructional.setAddress(request.getAddress());
    instructional.setInstructionalCode(request.getInstructionalCode());
    instructional.setPhone(request.getPhone());
    instructional.setUniversity(University.builder().id(request.getUniversityId()).build());
  }
}
