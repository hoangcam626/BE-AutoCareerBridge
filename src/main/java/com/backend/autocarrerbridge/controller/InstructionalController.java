package com.backend.autocarrerbridge.controller;

import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS_MESSAGE;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.instructional.InstructionalRequest;
import com.backend.autocarrerbridge.dto.response.instructional.InstructionalResponse;
import com.backend.autocarrerbridge.service.InstructionalService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/instructional")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InstructionalController {

  private final InstructionalService instructionalService;

  @PostMapping("/create")
  public ResponseEntity<ApiResponse<Object>> createInstructional(
      @RequestBody InstructionalRequest request) {
    InstructionalResponse createdInstructional = instructionalService.createInstructional(request);
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
        .setData(createdInstructional);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/update/{id}")
  public ApiResponse<Object> updateInstructional(@PathVariable("id") int id,
      @ModelAttribute InstructionalRequest request) {
    InstructionalResponse updatedInstructional = instructionalService.updateInstructional(id,
        request);
    return new ApiResponse<>().setData(updatedInstructional);
  }

  @GetMapping("/get-all")
  public ResponseEntity<ApiResponse<Object>> getAllInstructional() {
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
        .setData(instructionalService.getAllInstructional());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/getById/{id}")
  public ResponseEntity<ApiResponse<Object>> getInstructionalById(@PathVariable("id") int id) {
    ApiResponse<Object> response = new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
        .setData(instructionalService.getInstructionalById(id));
    return ResponseEntity.ok(response);
  }
}
