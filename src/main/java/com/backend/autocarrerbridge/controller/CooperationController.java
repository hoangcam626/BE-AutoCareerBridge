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

@RestController // Đánh dấu lớp này là một RESTful controller
@RequiredArgsConstructor // Tự động sinh constructor với các trường được đánh dấu là final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // Thiết lập phạm vi truy cập mặc định và đánh dấu các trường là final
@RequestMapping("api/cooperation") // Định nghĩa đường dẫn cơ sở cho các API
public class CooperationController {

    // Dịch vụ xử lý logic liên quan đến yêu cầu hợp tác
    BusinessUniversityService businessUniversityService;

    /**
     * API để lấy danh sách yêu cầu đã gửi từ doanh nghiệp
     *
     * @return danh sách yêu cầu đã gửi
     * @throws ParseException nếu có lỗi khi xử lý dữ liệu ngày tháng
     */
    @GetMapping("/get-request")
    ApiResponse<Object> getSentRequest() throws ParseException {
        return businessUniversityService.getSentRequest();
    }

    /**
     * API để gửi yêu cầu hợp tác từ doanh nghiệp tới trường đại học
     *
     * @param universityId ID của trường đại học
     * @return kết quả gửi yêu cầu
     * @throws ParseException nếu có lỗi khi xử lý dữ liệu ngày tháng
     */
    @PostMapping("/send-request")
    ApiResponse<Object> sendRequest(@RequestParam Integer universityId) throws ParseException {
        return businessUniversityService.sendRequest(universityId);
    }

    /**
     * API để hủy yêu cầu hợp tác đã gửi tới trường đại học
     *
     * @param universityId ID của trường đại học
     * @return kết quả hủy yêu cầu
     * @throws ParseException nếu có lỗi khi xử lý dữ liệu ngày tháng
     */
    @PutMapping("/cancel-request")
    ApiResponse<Object> cancelRequest(@RequestParam Integer universityId) throws ParseException {
        return businessUniversityService.cancelRequest(universityId);
    }

    /**
     * API để lấy tất cả các yêu cầu hợp tác
     *
     * @return danh sách tất cả các yêu cầu hợp tác
     * @throws ParseException nếu có lỗi khi xử lý dữ liệu ngày tháng
     */
    @GetMapping("/get-all-request")
    ApiResponse<List<CooperationUniversityResponse>> getAllRequestForUniversity() throws ParseException {
        return ApiResponse.<List<CooperationUniversityResponse>>builder()
                .data(businessUniversityService.getAllCooperationOfUniversity())
                .build();
    }

    /**
     * API để lấy danh sách yêu cầu hợp tác đang chờ xử lý
     *
     * @return danh sách các yêu cầu đang chờ xử lý
     * @throws ParseException nếu có lỗi khi xử lý dữ liệu ngày tháng
     */
    @GetMapping("/get-all-request-pending")
    ApiResponse<List<CooperationUniversityResponse>> getAllRequestForUniversityPending() throws ParseException {
        return ApiResponse.<List<CooperationUniversityResponse>>builder()
                .data(businessUniversityService.getAllCooperationOfUniversityPending())
                .build();
    }

    /**
     * API để lấy danh sách yêu cầu hợp tác đã được phê duyệt
     *
     * @return danh sách các yêu cầu đã được phê duyệt
     * @throws ParseException nếu có lỗi khi xử lý dữ liệu ngày tháng
     */
    @GetMapping("/get-all-request-approve")
    ApiResponse<List<CooperationUniversityResponse>> getAllRequestForUniversityApprove() throws ParseException {
        return ApiResponse.<List<CooperationUniversityResponse>>builder()
                .data(businessUniversityService.getAllCooperationOfUniversityApprove())
                .build();
    }

    /**
     * API để lấy danh sách yêu cầu hợp tác bị từ chối
     *
     * @return danh sách các yêu cầu bị từ chối
     * @throws ParseException nếu có lỗi khi xử lý dữ liệu ngày tháng
     */
    @GetMapping("/get-all-request-reject")
    ApiResponse<List<CooperationUniversityResponse>> getAllRequestForUniversityReject() throws ParseException {
        return ApiResponse.<List<CooperationUniversityResponse>>builder()
                .data(businessUniversityService.getAllCooperationOfUniversityReject())
                .build();
    }

    /**
     * API để phê duyệt yêu cầu hợp tác của doanh nghiệp
     *
     * @param buId ID của yêu cầu hợp tác
     * @return thông báo thành công
     */
    @GetMapping("/approve-request/{buId}")
    ApiResponse<String> approveRequestCooperation(@PathVariable Integer buId){
        businessUniversityService.approveRequetCooperation(buId);
        return ApiResponse.<String>builder()
                .data(SUCCESS_MESSAGE)
                .build();
    }

}