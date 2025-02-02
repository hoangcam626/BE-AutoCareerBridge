package com.backend.autocarrerbridge.controller;

import static com.backend.autocarrerbridge.util.Constant.REGISTER_UNIVERSITY;
import static com.backend.autocarrerbridge.util.Constant.SEND_CODE;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS_MESSAGE;

import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.backend.autocarrerbridge.dto.request.university.UniversityRequest;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
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
    public ApiResponse<Object> registerUniversity(@RequestBody @Valid UserUniversityRequest userUniversityRequest) {

        return ApiResponse.builder()
                .code(SUCCESS)
                .message(REGISTER_UNIVERSITY)
                .data(universityService.registerUniversity(userUniversityRequest))
                .build();
    }
    @PostMapping("/verify-university")
    public ApiResponse<Object> verifyUniversity(@RequestBody @Valid UserUniversityRequest userUniversityRequest){
        return ApiResponse.builder()
                .code(SUCCESS)
                .message(SEND_CODE)
                .data(universityService.generateCode(userUniversityRequest))
                .build();
    }
    @PostMapping("/update/{id}")
    public ApiResponse<Object> updateUniversity(
          @Valid  @PathVariable("id") int id, @ModelAttribute UniversityRequest universityRequest) {
        UniversityResponse updateUniversity = universityService.update(id, universityRequest);
        return new ApiResponse<>().setData(updateUniversity);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse<Object> getById(@PathVariable("id") int id) {
        return new ApiResponse<>().setData(universityService.getById(id));
    }

    @GetMapping("get-all")
    public ApiResponse<Object> getAll() {
        return new ApiResponse<>().setData(universityService.getAll());
    }

    @GetMapping("/search")
    public ApiResponse<Object> findUniversityByLocationOrName(
            @RequestParam(required = false) String address, @RequestParam(required = false) String universityName) {
        return ApiResponse.builder()
                .code(SUCCESS)
                .message(SUCCESS_MESSAGE)
                .data(universityService.findUniversityByNameOrLocation(address, universityName))
                .build();
    }
    @GetMapping("/get-total")
    public ApiResponse<Object> findTotalUniversity() {
        return ApiResponse.builder()
                .code(SUCCESS)
                .message(SUCCESS_MESSAGE)
                .data(universityService.getAllTotalUniversity())
                .build();
    }
    @GetMapping("/countTotalUniversity")
    public ApiResponse<Object> getTotalJob() {
        return ApiResponse.builder().data(universityService.countUniversityTotal()).build();
    }

    @GetMapping("/list-home")
    ApiResponse<Object> getBusinessListHome() {
        return ApiResponse.builder()
                .data(universityService.getUniversityListHome())
                .build();
    }
    @GetMapping("/get-all-university-page")
    public ApiResponse<Object> getAllUniversityPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String keyword
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ApiResponse.builder()
                .data(universityService.getAllUniversityPage(keyword, pageable))
                .build();
    }
}
