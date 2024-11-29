package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.response.cooperation.CooperationUniversityResponse;
import com.backend.autocarrerbridge.service.BusinessUniversityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

import static com.backend.autocarrerbridge.util.Constant.SUCCESS_MESSAGE;

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
    ApiResponse<Object> getSentRequest() throws ParseException {
        return businessUniversityService.getSentRequest();
    }

    /**
     * API để doanh nghiệp gửi yêu cầu hợp tác tới trường đại học
     *
     * @apiNote để để thêm yêu cầu vào cơ sở dữ liệu
     */
    @PostMapping("/send-request")
    ApiResponse<Object> sendRequest(@RequestParam Integer universityId) throws ParseException {
        return businessUniversityService.sendRequest(universityId);
    }

    /**
     * API để doanh nghiệp gửi yêu cầu hợp tác tới trường đại học
     *
     * @apiNote để để thêm yêu cầu vào cơ sở dữ liệu
     */
    @PutMapping("/cancel-request")
    ApiResponse<Object> cancelRequest(@RequestParam Integer universityId) throws ParseException {
        return businessUniversityService.cancelRequest(universityId);
    }

    @GetMapping("/get-all-request")
    ApiResponse<List<CooperationUniversityResponse>> getAllRequestForUniversity() throws ParseException {
        return ApiResponse.<List<CooperationUniversityResponse>>builder()
                .data(businessUniversityService.getAllCooperationOfUniversity())
                .build();
    }

    @GetMapping("/get-all-request-pending")
    ApiResponse<List<CooperationUniversityResponse>> getAllRequestForUniversityPending() throws ParseException {
        return ApiResponse.<List<CooperationUniversityResponse>>builder()
                .data(businessUniversityService.getAllCooperationOfUniversityPending())
                .build();
    }

    @GetMapping("/get-all-request-approve")
    ApiResponse<List<CooperationUniversityResponse>> getAllRequestForUniversityApprove() throws ParseException {
        return ApiResponse.<List<CooperationUniversityResponse>>builder()
                .data(businessUniversityService.getAllCooperationOfUniversityApprove())
                .build();
    }

    @GetMapping("/get-all-request-reject")
    ApiResponse<List<CooperationUniversityResponse>> getAllRequestForUniversityReject() throws ParseException {
        return ApiResponse.<List<CooperationUniversityResponse>>builder()
                .data(businessUniversityService.getAllCooperationOfUniversityReject())
                .build();
    }

    @GetMapping("/approve-request/{buId}")
    ApiResponse<String> approveRequestCooperation(@PathVariable Integer buId){
        businessUniversityService.approveRequetCooperation(buId);
        return ApiResponse.<String>builder()
                .data(SUCCESS_MESSAGE)
                .build();
    }

}
