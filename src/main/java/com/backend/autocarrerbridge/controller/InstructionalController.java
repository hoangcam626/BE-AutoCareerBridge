package com.backend.autocarrerbridge.controller;

import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS_MESSAGE;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.instructional.InstructionalRequest;
import com.backend.autocarrerbridge.dto.response.instructional.InstructionalResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.service.InstructionalService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/instructional")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InstructionalController {

  private final InstructionalService instructionalService;
  /**
   * Tạo mới một Instructional (Giáo vụ).
   *
   * @param request Thông tin yêu cầu tạo mới Instructional.
   * @return Đối tượng ResponseEntity chứa ApiResponse với thông tin của Instructional mới tạo.
   */
  @PostMapping("/create")
  public ResponseEntity<ApiResponse<Object>> createInstructional(
    @Valid @RequestBody InstructionalRequest request) {
    InstructionalResponse createdInstructional = instructionalService.createInstructional(request);
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
        .setData(createdInstructional);
    return ResponseEntity.ok(response);
  }
  /**
   * Cập nhật một Instructional (Giáo vụ) theo ID.
   *
   * @param id      ID của Instructional cần cập nhật.
   * @param request Thông tin yêu cầu cập nhật Instructional.
   * @return Đối tượng ApiResponse với thông tin của Instructional đã được cập nhật.
   */
  @PostMapping("/update/{id}")
  public ResponseEntity<ApiResponse<Object>> updateInstructional(@PathVariable("id") int id, @ModelAttribute InstructionalRequest request) {
    InstructionalResponse updatedInstructional = instructionalService.updateInstructional(id, request);
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE).setData(updatedInstructional);
    return ResponseEntity.ok(response);
  }
  /**
   * Xóa một Instructional (Giáo vụ) theo ID.
   *
   * @return Đối tượng ResponseEntity chứa ApiResponse với thông tin của Instructional đã được xóa.
   */
  @DeleteMapping("/delete")
  public ResponseEntity<ApiResponse<Object>> deleteInstructional(@RequestBody List<Integer> ids) {
    List<InstructionalResponse> deletedInstructionals = instructionalService.deleteInstructional(ids);
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
        .setData(deletedInstructionals);
    return ResponseEntity.ok(response);
  }
  /**
   * Lấy danh sách tất cả các Instructional (Giáo vụ) có phân trang.
   *
   * @param page Số trang hiện tại.
   * @param size Kích thước trang.
   * @return Đối tượng ResponseEntity chứa ApiResponse với Page các InstructionalResponse.
   */
  @GetMapping("/get-all")
  public ResponseEntity<ApiResponse<Object>> getAllInstructional( @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size ,@RequestParam Integer universityId) {
    PagingResponse<InstructionalResponse> instructionalPage = instructionalService.getAllInstructional(universityId,page, size);
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
        .setData(instructionalPage);
    return ResponseEntity.ok(response);
  }
  /**
   * Lấy một Instructional (Giáo vụ) theo ID.
   *
   * @param id ID của Instructional cần lấy.
   * @return Đối tượng ResponseEntity chứa ApiResponse với thông tin của Instructional.
   */
  @GetMapping("/getById/{id}")
  public ResponseEntity<ApiResponse<Object>> getInstructionalById(@PathVariable("id") int id) {
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
        .setData(instructionalService.getInstructionalById(id));
    return ResponseEntity.ok(response);
  }
  /**
   * Đặt trạng thái không hoạt động cho một Instructional (Giáo vụ) theo ID.
   *
   * @param id ID của Instructional cần đặt trạng thái không hoạt động.
   * @return Đối tượng ResponseEntity chứa ApiResponse với thông tin của Instructional đã được cập nhật.
   */
  @PostMapping("/inactive/{id}")
  public ResponseEntity<ApiResponse<Object>> inActiveInstructional(@PathVariable("id") int id) {
    InstructionalResponse setInactiveInstructional = instructionalService.setInstructionalInactive(
        id);
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
        .setData(setInactiveInstructional);
    return ResponseEntity.ok(response);
  }
  /**
   * Đặt trạng thái hoạt động cho một Instructional (Giáo vụ) theo ID.
   *
   * @param id ID của Instructional cần đặt trạng thái hoạt động.
   * @return Đối tượng ResponseEntity chứa ApiResponse với thông tin của Instructional đã được cập nhật.
   */
  @PostMapping("/active/{id}")
  public ResponseEntity<ApiResponse<Object>> activeInstructional(@PathVariable("id") int id) {
    InstructionalResponse setActiveInstructional = instructionalService.setInstructionalActive(id);
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
        .setData(setActiveInstructional);
    return ResponseEntity.ok(response);
  }
  /**
   * Lấy danh sách tất cả các Instructional (Giáo vụ) đang hoạt động.
   *
   * @return Đối tượng ResponseEntity chứa ApiResponse với danh sách các InstructionalResponse đang hoạt động.
   */
  @GetMapping("/get-all-active")
  public ResponseEntity<ApiResponse<Object>> getAllActiveInstructional(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size , @RequestParam Integer universityId) {
    PagingResponse<InstructionalResponse> instructionalPage = instructionalService.getALlInstructionalActive(universityId,page, size);
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
        .setData(instructionalPage);
    return ResponseEntity.ok(response);
  }
  /**
   * Lấy danh sách tất cả các Instructional (Giáo vụ) không hoạt động.
   *
   * @return Đối tượng ResponseEntity chứa ApiResponse với danh sách các InstructionalResponse không hoạt động.
   */
  @GetMapping("/get-all-inactive")
  public ResponseEntity<ApiResponse<Object>> getAllInActiveInstructional(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size, @RequestParam Integer universityId) {
    PagingResponse<InstructionalResponse> instructionalPage = instructionalService.getALlInstructionalInactive(universityId,page, size);
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
        .setData(instructionalPage);
    return ResponseEntity.ok(response);
  }

  @GetMapping("count-total/{universityId}")
  public long countInstructionals(@PathVariable Integer universityId) {
    return instructionalService.countInstructional(universityId);
  }
}
