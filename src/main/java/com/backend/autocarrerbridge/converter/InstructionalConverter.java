package com.backend.autocarrerbridge.converter;

import static com.backend.autocarrerbridge.util.Constant.SUB;

import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.util.enums.Status;
import java.text.ParseException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.backend.autocarrerbridge.dto.request.instructional.InstructionalRequest;
import com.backend.autocarrerbridge.dto.response.instructional.InstructionalResponse;
import com.backend.autocarrerbridge.entity.Instructional;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.service.impl.TokenServiceImpl;


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
    Instructional instructional = new Instructional();
    instructional.setId(request.getId());
    instructional.setName(request.getName());
    instructional.setGender(request.getGender());
    instructional.setEmail(request.getEmail());
    instructional.setInstructionalCode(request.getInstructionalCode());
    instructional.setPhone(request.getPhone());
    instructional.setUniversity(University.builder().id(request.getUniversityId()).build());
    instructional.setUserAccount(UserAccount.builder().id(request.getUserAccountId()).build());
    instructional.setStatus(request.getStatus()!=null ? request.getStatus():Status.ACTIVE);
    instructional.setCreatedAt(request.getCreatedAt()!=null ? request.getCreatedAt() :LocalDateTime.now());
    instructional.setUpdatedAt(request.getUpdatedAt()!= null? request.getUpdatedAt() : LocalDateTime.now());

    try {
      String currentUser = tokenService.getClaim(tokenService.getJWT(), SUB);
      instructional.setCreatedBy(currentUser);
      instructional.setUpdatedBy(currentUser);
    } catch (ParseException e) {

      throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
    }
    return instructional;
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
    InstructionalResponse response = InstructionalResponse.builder()
        .id(instructional.getId())
        .name(instructional.getName())
        .gender(instructional.getGender())
        .dateOfBirth(instructional.getDateOfBirth())
        .email(instructional.getEmail())
        .address(instructional.getAddress())
        .instructionalCode(instructional.getInstructionalCode())
        .instructionalImageId(instructional.getInstructionalImageId())
        .phone(instructional.getPhone())
        .universityId(instructional.getUniversity().getId())
        .userAccountId(instructional.getUserAccount().getId())
        .build();
    response.setStatus(instructional.getStatus());
    response.setCreatedAt(instructional.getCreatedAt());
    response.setUpdatedAt(instructional.getUpdatedAt());
    response.setCreatedBy(instructional.getCreatedBy());
    response.setUpdatedBy(instructional.getUpdatedBy());
    return response;
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
