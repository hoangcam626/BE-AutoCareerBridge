package com.backend.autocarrerbridge.controller;

import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS_MESSAGE;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.major.MajorRequest;
import com.backend.autocarrerbridge.service.MajorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/major")
@RequiredArgsConstructor
public class MajorController {

  private final MajorService majorService;

  @PostMapping("/create")
  public ResponseEntity<ApiResponse<Object>> create(@Valid @RequestBody MajorRequest majorRequest) {
    MajorRequest createMajor = majorService.createMajor(majorRequest);
    ApiResponse<Object> response =
        new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE).setData(createMajor);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/update/{id}")
  public ResponseEntity<ApiResponse<Object>> update(
      @Valid @PathVariable("id") int id, @RequestBody MajorRequest majorRequest) {
    MajorRequest updateMajor = majorService.updateMajor(id, majorRequest);
    ApiResponse<Object> response =
        new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE).setData(updateMajor);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ApiResponse<Object>> delete(@RequestBody List<Integer> ids) {
    List<MajorRequest> deleteMajor = majorService.deleteMajor(ids);
    ApiResponse<Object> response =
        new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE).setData(deleteMajor);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/getById/{id}")
  public ResponseEntity<ApiResponse<Object>> getById(@PathVariable("id") int id) {
    ApiResponse<Object> response =
        new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
            .setData(majorService.getMajorById(id));
    return ResponseEntity.ok(response);
  }

  @GetMapping("/get-all/{universityId}")
  public ResponseEntity<ApiResponse<Object>> getAll(@PathVariable Integer universityId) {
    ApiResponse<Object> response =
        new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE)
            .setData(majorService.getAllMajor(universityId));
    return ResponseEntity.ok(response);
  }

  @PostMapping("/inactive/{id}")
  public ResponseEntity<ApiResponse<Object>> inActiveMajor(@PathVariable("id") int id) {
    MajorRequest setMajorInactive = majorService.setMajorInactive(id);
    ApiResponse<Object> response =
        new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE).setData(setMajorInactive);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/active/{id}")
  public ResponseEntity<ApiResponse<Object>> activeMajor(@PathVariable("id") int id) {
    MajorRequest setMajorActive = majorService.setMajorActive(id);
    ApiResponse<Object> response =
        new ApiResponse<>().setCode(SUCCESS).setMessage(SUCCESS_MESSAGE).setData(setMajorActive);
    return ResponseEntity.ok(response);
  }

  @GetMapping("count-total/{universityId}")
  public long countMajor(@PathVariable Integer universityId) {
    return majorService.countMajor(universityId);
  }

  @GetMapping("count-total-student/{universityId}")
  public int countStudent(@PathVariable Integer universityId) {
    return majorService.getTotalNumberStudents(universityId);
  }
  @GetMapping("/count-student/{universityId}")
  public Map<String, Integer> countStudentInMajor(@PathVariable Integer universityId){
    return majorService.getNumberStudentsInMajor(universityId);
  }
}
