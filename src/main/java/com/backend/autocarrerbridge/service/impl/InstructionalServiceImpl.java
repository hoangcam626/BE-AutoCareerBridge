package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.repository.InstructionalRepository;
import static com.backend.autocarrerbridge.util.Constant.ACCOUNT;
import static com.backend.autocarrerbridge.util.Constant.SUB;

import com.backend.autocarrerbridge.converter.InstructionalConverter;
import com.backend.autocarrerbridge.entity.Instructional;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.InstructionalRepository;
import com.backend.autocarrerbridge.dto.request.instructional.InstructionalRequest;
import com.backend.autocarrerbridge.dto.response.instructional.InstructionalResponse;
import com.backend.autocarrerbridge.service.ImageService;
import com.backend.autocarrerbridge.service.InstructionalService;
import com.backend.autocarrerbridge.service.RoleService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.email.EmailDTO;
import com.backend.autocarrerbridge.util.email.SendEmail;
import com.backend.autocarrerbridge.util.enums.PredefinedRole;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;
import com.backend.autocarrerbridge.util.password.PasswordGenerator;
import jakarta.transaction.Transactional;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InstructionalServiceImpl implements InstructionalService {

  InstructionalRepository instructionalRepository;
  RoleService roleService;
  UserAccountService userAccountService;
  TokenServiceImpl tokenService;
  SendEmail sendEmail;
  InstructionalConverter instructionalConverter;
  ImageService imageService;


  /**
   * Tạo mới một Instructional (Giáo vụ).
   *
   * @param request Thông tin Giáo vụ từ yêu cầu.
   * @return Đối tượng InstructionalResponse chứa thông tin Giáo vụ đã tạo.
   */
  @Transactional
  @Override
  public InstructionalResponse createInstructional(InstructionalRequest request) {
    // Chuyển đổi request thành entity Instructional
    Instructional instructional = InstructionalConverter.toEntity(request);
    instructional.setStatus(Status.ACTIVE);
    instructional.setCreatedAt(LocalDateTime.now());
    instructional.setUpdatedAt(LocalDateTime.now());
    try {
      // Lấy thông tin người dùng hiện tại từ JWT token
      String currentUser = tokenService.getClaim(tokenService.getJWT(), SUB);
      instructional.setCreatedBy(currentUser);
      instructional.setUpdatedBy(currentUser);
    } catch (ParseException e) {
      // Ném ngoại lệ nếu token không hợp lệ
      throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
    }
    // Tạo mật khẩu ngẫu nhiên cho tài khoản người dùng
    PasswordGenerator passwordGenerator = new PasswordGenerator(8, 12);
    String password = passwordGenerator.generatePassword();
    UserAccount userAccount = new UserAccount();
    userAccount.setUsername(request.getEmail());
    userAccount.setPassword(password);
    userAccount.setState(State.APPROVED);
    userAccount.setRole(roleService.findById(PredefinedRole.INSTRUCTIONAL.getValue()));
    instructional.setUserAccount(userAccountService.registerUser(userAccount));
    //Lưu vào entity
    instructional = instructionalRepository.save(instructional);
    // Gửi email thông báo tài khoản
    EmailDTO emailDTO = new EmailDTO(instructional.getEmail(), ACCOUNT, "");
    sendEmail.sendAccount(emailDTO, password);
    // Trả về phản hồi sau khi tạo thành công
    return InstructionalConverter.toResponse(instructional);
  }

  /**
   * Cập nhật thông tin của một Instructional (Giáo vụ) theo ID.
   *
   * @param id      ID của Giáo vụ cần cập nhật.
   * @param request Thông tin cập nhật cho Giáo vụ.
   * @return Đối tượng InstructionalResponse chứa thông tin Giáo vụ đã cập nhật.
   */
  @Transactional
  @Override
  public InstructionalResponse updateInstructional(int id, InstructionalRequest request) {
    Instructional instructional = instructionalRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTIONS_NOT_EXIST));
    instructionalConverter.updateEntityFormRequest(request, instructional);
    // Cập nhật ảnh
    if (request.getInstructionalImageId() != null && !request.getInstructionalImageId().isEmpty()) {
      instructional.setInstructionalImageId(imageService.uploadFile(request.getInstructionalImageId()));
    }
    // Cập nhật thông tin người dùng hiện tại vào các trường updatedBy và updatedAt
    try {
      String currentUser = tokenService.getClaim(tokenService.getJWT(), SUB);
      instructional.setUpdatedBy(currentUser);
      instructional.setUpdatedAt(LocalDateTime.now());
    } catch (ParseException e) {
      throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
    }
    // Lưu thay đổi vào database
    instructional = instructionalRepository.save(instructional);
    // Trả về phản hồi chứa thông tin của Instructional đã được cập nhật
    return InstructionalConverter.toResponse(instructional);
  }

  /**
   * Xóa một Instructional (Giáo vụ) theo ID bằng cách thay đổi trạng thái thành INACTIVE.
   *
   * @param id ID của Giáo vụ cần xóa.
   * @return Đối tượng InstructionalResponse chứa thông tin Giáo vụ đã cập nhật trạng thái.
   */
  @Override
  public InstructionalResponse deleteInstructional(int id) {
    Instructional instructional = instructionalRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTIONS_NOT_EXIST));
    instructional.setStatus(Status.INACTIVE);
    instructional.setUpdatedAt(LocalDateTime.now());
    // Lưu thay đ��i vào database
    instructionalRepository.save(instructional);
    // Trả về phản hồi chứa thông tin của Instructional đã xóa
    return InstructionalConverter.toResponse(instructional);
  }

  /**
   * Lấy danh sách tất cả các Instructional (Giáo vụ).
   *
   * @return Danh sách các InstructionalResponse chứa thông tin của tất cả Giáo vụ.
   */
  @Override
  public List<InstructionalResponse> getAllInstructional() {
    List<Instructional> instructional = instructionalRepository.findAll();
    instructional.sort(Comparator.comparingLong(Instructional::getId).reversed());
    return instructional.stream().map(InstructionalConverter::toResponse)
        .collect(Collectors.toList());
  }

  /**
   * Lấy thông tin một Instructional (Giáo vụ) theo ID.
   *
   * @param id ID của Giáo vụ cần lấy thông tin.
   * @return Danh sách chứa một InstructionalResponse với thông tin của Giáo vụ.
   */
  @Override
  public List<InstructionalResponse> getInstructionalById(int id) {
    Instructional instructional = instructionalRepository.findById(id)
        .orElseThrow(() -> new AppException(
            ErrorCode.INSTRUCTIONS_NOT_EXIST));
    return List.of(InstructionalConverter.toResponse(instructional));
  }
}
