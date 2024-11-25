package com.backend.autocarrerbridge.controller;

import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS_MESSAGE;

import com.backend.autocarrerbridge.dto.request.section.SectionRequest;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.service.SectionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/section")
@RequiredArgsConstructor
public class SectionController {

  private final SectionService sectionService;

  @PostMapping("/create")
  public ResponseEntity<ApiResponse<Object>> create(@Valid @RequestBody SectionRequest sectionRequest) {
    SectionRequest createSection = sectionService.createSection(sectionRequest);
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(createSection);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/update/{id}")
  public ResponseEntity<ApiResponse<Object>> update(@Valid @PathVariable("id") int id,
      @RequestBody SectionRequest sectionRequest) {
    SectionRequest updateSection = sectionService.updateSection(id, sectionRequest);
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(updateSection);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ApiResponse<Object>> delete(@PathVariable("id") int id) {
    SectionRequest deleteSection = sectionService.deleteSection(id);
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(deleteSection);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/getById/{id}")
  public ResponseEntity<ApiResponse<Object>> getById(@PathVariable("id") int id) {
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(sectionService.getSectionById(id));
    return ResponseEntity.ok(response);
  }


  @GetMapping("/get-all")
  public ResponseEntity<ApiResponse<Object>> getAll() {
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(sectionService.getAllSection());
    return ResponseEntity.ok(response);
  }

}