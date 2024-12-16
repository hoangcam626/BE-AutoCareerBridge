package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.repository.InstructionalRepository;

import static com.backend.autocarrerbridge.util.Constant.ACCOUNT;
import static com.backend.autocarrerbridge.util.Constant.SUB;

import com.backend.autocarrerbridge.converter.InstructionalConverter;
import com.backend.autocarrerbridge.entity.Instructional;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
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
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    if(instructionalRepository.findByInstructionalCode(request.getInstructionalCode())!=null) {
      throw new AppException(ErrorCode.INSTRUCTIONAL_CODE_EXISTED);
    }

    // Chuyển đổi request thành entity Instructional
    Instructional instructional = InstructionalConverter.toEntity(request);

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
      instructional.setInstructionalImageId(
          imageService.uploadFile(request.getInstructionalImageId()));
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
   * Thiết lập trạng thái của một Instructional (Giáo vụ) thành INACTIVE.
   *
   * @param id ID của Giáo vụ cần thay đổi trạng thái.
   * @return Đối tượng InstructionalResponse chứa thông tin Giáo vụ đã cập nhật trạng thái.
   */
  @Transactional
  @Override
  public InstructionalResponse setInstructionalInactive(int id) {
    Instructional instructional = instructionalRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTIONS_NOT_EXIST));
    instructional.setStatus(Status.INACTIVE);
    instructional.setUpdatedAt(LocalDateTime.now());
    instructionalRepository.save(instructional);
    return InstructionalConverter.toResponse(instructional);
  }

  /**
   * Thiết lập trạng thái của một Instructional (Giáo vụ) thành ACTIVE.
   *
   * @param id ID của Giáo vụ cần thay đổi trạng thái.
   * @return Đối tượng InstructionalResponse chứa thông tin Giáo vụ đã cập nhật trạng thái.
   */
  @Transactional
  @Override
  public InstructionalResponse setInstructionalActive(int id) {
    Instructional instructional = instructionalRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTIONS_NOT_EXIST));
    instructional.setStatus(Status.ACTIVE);
    instructional.setUpdatedAt(LocalDateTime.now());
    instructionalRepository.save(instructional);
    return InstructionalConverter.toResponse(instructional);
  }

  /**
   * Xóa một Instructional (Giáo vụ) theo ID bằng cách thay đổi trạng thái thành INACTIVE.
   *
   * @param ids ID của Giáo vụ cần xóa.
   * @return Đối tượng InstructionalResponse chứa thông tin Giáo vụ đã cập nhật trạng thái.
   */
  @Transactional
  @Override
  public List<InstructionalResponse> deleteInstructional(List<Integer> ids) {
    // Duyệt qua từng ID trong danh sách
    List<Instructional> instructionalList = ids.stream().map(id -> {
      Instructional instructional = instructionalRepository.findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTIONS_NOT_EXIST));
      // Lưu thay đổi vào database (thực hiện xoá)
      instructionalRepository.delete(instructional);
      return instructional;
    }).toList();
    // Trả về phản hồi chứa thông tin của Instructional đã xóa
    return instructionalList.stream()
        .map(InstructionalConverter::toResponse)
        .collect(Collectors.toList());
  }

  /**
   * Lấy thông tin một Instructional (Giáo vụ) theo ID.
   *
   * @param id ID của Giáo vụ cần lấy thông tin.
   * @return Danh sách chứa một InstructionalResponse với thông tin của Giáo vụ.
   */
  @Transactional
  @Override
  public List<InstructionalResponse> getInstructionalById(int id) {
    Instructional instructional = instructionalRepository.findById(id)
        .orElseThrow(() -> new AppException(
            ErrorCode.INSTRUCTIONS_NOT_EXIST));
    return List.of(InstructionalConverter.toResponse(instructional));
  }

  /**
   * Lấy danh sách tất cả các Instructional (Giáo vụ).
   *
   * @return Danh sách các InstructionalResponse chứa thông tin của tất cả Giáo vụ.
   */
  @Transactional
  @Override
  public PagingResponse<InstructionalResponse> getAllInstructional(int page, int size) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Instructional> instructionalPage = instructionalRepository.findAll(pageable);
    Page<InstructionalResponse> instructionalResponsePage = instructionalPage.map(
        InstructionalConverter::toResponse);
    return new PagingResponse<>(instructionalResponsePage);
  }

  /**
   * Phương thức này lấy danh sách các Giáo vụ đang hoạt động (Active) từ cơ sở dữ liệu với phân
   * trang.
   *
   * @param page Chỉ số trang cần lấy (bắt đầu từ 0).
   * @param size Số lượng phần tử trên mỗi trang.
   * @return Danh sách các Giáo vụ đang hoạt động dưới dạng `Page<InstructionalResponse>`, chứa các
   * phần tử được chuyển đổi từ `Instructional` và thông tin phân trang như tổng số phần tử, số
   * trang.
   */
  @Override
  public PagingResponse<InstructionalResponse> getALlInstructionalActive(int page, int size) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Instructional> instructionalPage = instructionalRepository.findAllActive(pageable);
    Page<InstructionalResponse> instructionalResponsePage = instructionalPage.map(
        InstructionalConverter::toResponse);
    return new PagingResponse<>(instructionalResponsePage);
  }

  /**
   * Phương thức này lấy danh sách các Giáo vụ không hoạt động (Inactive) từ cơ sở dữ liệu với phân
   * trang.
   *
   * @param page Chỉ số trang cần lấy (bắt đầu từ 0).
   * @param size Số lượng phần tử trên mỗi trang.
   * @return Danh sách các Giáo vụ không hoạt động dưới dạng `Page<InstructionalResponse>`, chứa các
   * phần tử được chuyển đổi từ `Instructional` và thông tin phân trang như tổng số phần tử, số
   * trang.
   */
  @Override
  public PagingResponse<InstructionalResponse> getALlInstructionalInactive(int page, int size) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Instructional> instructionalPage = instructionalRepository.findAllInactive(pageable);
    Page<InstructionalResponse> instructionalResponsePage = instructionalPage.map(
        InstructionalConverter::toResponse);
    return new PagingResponse<>(instructionalResponsePage);
  }
}
