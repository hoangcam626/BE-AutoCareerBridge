package com.backend.autocarrerbridge.controller;

import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS_MESSAGE;

import com.backend.autocarrerbridge.dto.SectionDTO;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.service.impl.SectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/section")
public class SectionController {

  @Autowired
  private SectionServiceImpl sectionService;

  @PostMapping("/create")
  public ResponseEntity<ApiResponse<Object>> create(@RequestBody SectionDTO sectionDTO) {
    SectionDTO createSection = sectionService.createSection(sectionDTO);
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(createSection);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/update/{id}")
  public ResponseEntity<ApiResponse<Object>> update(@PathVariable("id") int id,
      @RequestBody SectionDTO sectionDTO) {
    SectionDTO updateSection = sectionService.updateSection(id, sectionDTO);
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(updateSection);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ApiResponse<Object>> delete(@PathVariable("id") int id) {
    SectionDTO deleteSection = sectionService.deleteSection(id);
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


  @GetMapping("/getAll")
  public ResponseEntity<ApiResponse<Object>> getAll() {
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(sectionService.getAllSection());
    return ResponseEntity.ok(response);
  }

}
