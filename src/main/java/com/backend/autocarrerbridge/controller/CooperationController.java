package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.service.BusinessUniversityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/cooperation")
public class CooperationController {

    BusinessUniversityService businessUniversityService;


    /**
     * API để doanh nghiệp gửi yêu cầu hợp tác tới trường đại học
     *
     * @apiNote để để thêm yêu cầu vào cơ sở dữ liệu
     */
    @GetMapping("/get-request")
    private ApiResponse<Object> getSentRequest() throws ParseException {
        return businessUniversityService.getSentRequest();
    }

    /**
     * API để doanh nghiệp gửi yêu cầu hợp tác tới trường đại học
     *
     * @apiNote để để thêm yêu cầu vào cơ sở dữ liệu
     */
    @PostMapping("/send-request")
    private ApiResponse<Object> sendRequest(@RequestParam Integer universityId) throws ParseException {
        return businessUniversityService.sendRequest(universityId);
    }

    /**
     * API để doanh nghiệp gửi yêu cầu hợp tác tới trường đại học
     *
     * @apiNote để để thêm yêu cầu vào cơ sở dữ liệu
     */
    @PutMapping("/cancel-request")
    private ApiResponse<Object> cancelRequest(@RequestParam Integer universityId) throws ParseException {
        return businessUniversityService.cancelRequest(universityId);
    }
}
