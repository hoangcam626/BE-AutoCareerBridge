package com.backend.autocarrerbridge.controller;

import static com.backend.autocarrerbridge.util.Constant.REGISTER_UNIVERSITY;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS_MESSAGE;

import com.backend.autocarrerbridge.dto.request.university.UniversityRequest;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.account.UserUniversityRequest;
import com.backend.autocarrerbridge.service.UniversityService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/university")
public class UniversityController {

  UniversityService universityService;

  @PostMapping("/register")
  public ApiResponse<Object> registerUniversity(
      @RequestBody @Valid UserUniversityRequest userUniversityRequest) {

    return ApiResponse.builder()
        .code(SUCCESS)
        .message(REGISTER_UNIVERSITY)
        .data(universityService.registerUniversity(userUniversityRequest))
        .build();
  }

  @PostMapping("/update/{id}")
  public ResponseEntity<ApiResponse<Object>> updateUniversity(@Valid @PathVariable("id") int id,
      @ModelAttribute UniversityRequest universityRequest) {
    UniversityResponse updateUniversity = universityService.update(id, universityRequest);
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(updateUniversity);
    return ResponseEntity.ok(response);

  }

  @GetMapping("/getById/{id}")
  public ResponseEntity<ApiResponse<Object>> getById(@PathVariable("id") int id) {
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(universityService.getById(id));
    return ResponseEntity.ok(response);
  }
  @GetMapping("get-all")
  public ResponseEntity<ApiResponse<Object>> getAll() {
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(universityService.getAll());
    return ResponseEntity.ok(response);
  }
}
