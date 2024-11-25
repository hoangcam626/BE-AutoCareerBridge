package com.backend.autocarrerbridge.controller;


import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS_MESSAGE;
import com.backend.autocarrerbridge.dto.request.major.MajorRequest;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.service.MajorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/major")
@RequiredArgsConstructor
public class MajorController {

  private final MajorService majorService;

  @PostMapping("/create")
  public ResponseEntity<ApiResponse<Object>> create(@Valid @RequestBody MajorRequest majorRequest) {
    MajorRequest createMajor = majorService.createMajor(majorRequest);
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(createMajor);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/update/{id}")
  public ResponseEntity<ApiResponse<Object>> update(@Valid @PathVariable("id") int id,
      @RequestBody MajorRequest majorRequest) {
    MajorRequest updateMajor = majorService.updateMajor(id, majorRequest);
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(updateMajor);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ApiResponse<Object>> delete(@PathVariable("id") int id) {
    MajorRequest deleteMajor = majorService.deleteMajor(id);
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(deleteMajor);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/getById/{id}")
  public ResponseEntity<ApiResponse<Object>> getById(@PathVariable("id") int id) {
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(majorService.getMajorById(id));
    return ResponseEntity.ok(response);
  }


  @GetMapping("/getAll")
  public ResponseEntity<ApiResponse<Object>> getAll() {
    ApiResponse<Object> response = new ApiResponse<>()
        .setCode(SUCCESS)
        .setMessage(SUCCESS_MESSAGE)
        .setData(majorService.getAllMajor());
    return ResponseEntity.ok(response);
  }
}
